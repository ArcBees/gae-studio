/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.event.FullScreenEvent;
import com.arcbees.gaestudio.client.application.event.RowLockedEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.VisualizerPresenter;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.SetStateFromPlaceRequestEvent;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityEditorFactory;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityEditorPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.InvalidEntityFieldsException;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyEditorErrorEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.EntityService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.core.client.Scheduler;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import static com.arcbees.gaestudio.client.place.ParameterTokens.APP_ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.KIND;
import static com.arcbees.gaestudio.client.place.ParameterTokens.NAME;
import static com.arcbees.gaestudio.client.place.ParameterTokens.NAMESPACE;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_ID;
import static com.arcbees.gaestudio.client.place.ParameterTokens.PARENT_KIND;

public class EditEntityPresenter extends Presenter<EditEntityPresenter.MyView, EditEntityPresenter.MyProxy>
        implements EditEntityUiHandlers, PropertyEditorErrorEvent.PropertyEditorErrorHandler {
    interface MyView extends View, HasUiHandlers<EditEntityUiHandlers> {
        void showError(String message);

        void clearErrors();

        void showErrorsTitle(String title);
    }

    @ProxyStandard
    @NameToken(NameTokens.editEntity)
    interface MyProxy extends ProxyPlace<EditEntityPresenter> {
    }

    public static final Object EDITOR_SLOT = new Object();

    private final RestDispatch restDispatch;
    private final EntityService entityService;
    private final EntityEditorFactory entityEditorFactory;
    private final AppConstants appConstants;
    private final PlaceManager placeManager;

    private ParsedEntity currentEntity;
    private EntityEditorPresenter entityEditor;

    @Inject
    EditEntityPresenter(EventBus eventBus,
                        MyView view,
                        MyProxy proxy,
                        RestDispatch restDispatch,
                        PlaceManager placeManager,
                        EntityService entityService,
                        EntityEditorFactory entityEditorFactory,
                        AppConstants appConstants) {
        super(eventBus, view, proxy, VisualizerPresenter.SLOT_ENTITY_DETAILS);

        this.restDispatch = restDispatch;
        this.entityService = entityService;
        this.placeManager = placeManager;
        this.entityEditorFactory = entityEditorFactory;
        this.appConstants = appConstants;

        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(final PlaceRequest request) {
        super.prepareFromRequest(request);

        if (!isVisible()) {
            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    SetStateFromPlaceRequestEvent.fire(EditEntityPresenter.this, request);
                }
            });
        }

        editEntity(request);
    }

    @Override
    public void saveEntity() {
        getView().clearErrors();

        try {
            updateEntity();
        } catch (InvalidEntityFieldsException e) {
            getView().showErrorsTitle(appConstants.invalidFields());
        }
    }

    @Override
    public void cancelEdit() {
        revealDetailEntity();
    }

    @Override
    public void onPropertyEditorError(PropertyEditorErrorEvent event) {
        getView().showError(event.getError());
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(PropertyEditorErrorEvent.getType(), this);
    }

    private void onSaveEntityFailed(Throwable caught) {
        String message = caught.getMessage();
        if (message == null) {
            message = appConstants.errorSavingChanges();
        }
        getView().showError(message);
    }

    private void revealDetailEntity() {
        KeyDto keyDto = currentEntity.getKey();

        PlaceRequest.Builder builder = new PlaceRequest.Builder().nameToken(NameTokens.entity)
                .with(KIND, keyDto.getKind())
                .with(ID, Long.toString(keyDto.getId()))
                .with(NAME, keyDto.getName())
                .with(NAMESPACE, keyDto.getAppIdNamespace().getNamespace())
                .with(APP_ID, keyDto.getAppIdNamespace().getAppId());

        if (keyDto.getParentKey() != null) {
            builder = builder.with(PARENT_KIND, keyDto.getParentKey().getKind())
                    .with(PARENT_ID, Long.toString(keyDto.getParentKey().getId()));
        }

        placeManager.revealPlace(builder.build());
    }

    private void editEntity(PlaceRequest request) {
        String kind = request.getParameter(KIND, null);
        String id = request.getParameter(ID, "-1");
        String name = request.getParameter(NAME, null);
        String parentKind = request.getParameter(PARENT_KIND, "");
        String parentId = request.getParameter(PARENT_ID, "");
        String namespace = request.getParameter(NAMESPACE, null);
        String appId = request.getParameter(APP_ID, null);

        String failureMessage = appConstants.failedGettingEntity();
        AsyncCallbackImpl<EntityDto> callback = new AsyncCallbackImpl<EntityDto>(failureMessage) {
            @Override
            public void onSuccess(EntityDto result) {
                currentEntity = new ParsedEntity(result);
                entityEditor = entityEditorFactory.create(currentEntity);

                setInSlot(EDITOR_SLOT, entityEditor);
                RowLockedEvent.fire(this);
            }
        };

        RestAction<EntityDto> getEntityAction =
                entityService.getEntity(kind, appId, namespace, parentId, parentKind, name, Long.valueOf(id));

        restDispatch.execute(getEntityAction, callback);
    }

    private void onSaveEntitySucceeded(EntityDto newEntityDto) {
        EntitySavedEvent.fire(this, newEntityDto);
        Message message = new Message("Entity saved.", MessageStyle.SUCCESS);
        DisplayMessageEvent.fire(this, message);
        revealDetailEntity();
    }

    private void updateEntity() throws InvalidEntityFieldsException {
        EntityDto entityDto = entityEditor.flush().getEntityDto();

        restDispatch.execute(entityService.updateEntity(entityDto),
                new AsyncCallbackImpl<EntityDto>() {
                    @Override
                    public void handleFailure(Throwable caught) {
                        onSaveEntityFailed(caught);
                    }

                    @Override
                    public void onSuccess(EntityDto result) {
                        onSaveEntitySucceeded(result);
                    }
                });
    }
}
