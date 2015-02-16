/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.entity.editor.EntitiesEditorPresenter;
import com.arcbees.gaestudio.client.application.entity.editor.EntityEditorFactory;
import com.arcbees.gaestudio.client.application.entity.editor.EntityEditorPresenter;
import com.arcbees.gaestudio.client.application.entity.editor.InvalidEntityFieldsException;
import com.arcbees.gaestudio.client.application.entity.editor.PropertyEditorErrorEvent;
import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.VisualizerPresenter;
import com.arcbees.gaestudio.client.application.visualizer.event.EditEntitiesEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesSavedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.EntitiesService;
import com.arcbees.gaestudio.client.rest.EntityService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class EditEntityPresenter extends Presenter<EditEntityPresenter.MyView, EditEntityPresenter.MyProxy>
        implements EditEntityUiHandlers, PropertyEditorErrorEvent.PropertyEditorErrorHandler,
        EditEntitiesEvent.EntitiesSelectedHandler {
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
    private final EntitiesService entitiesService;
    private final EntityService entityService;
    private final EntityEditorFactory entityEditorFactory;
    private final AppConstants appConstants;
    private final PlaceManager placeManager;

    private ParsedEntity currentEntity;
    private EntityEditorPresenter entityEditor;
    private Set<ParsedEntity> currentEntities;
    private EntitiesEditorPresenter entitiesEditor;

    @Inject
    EditEntityPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            RestDispatch restDispatch,
            PlaceManager placeManager,
            EntitiesService entitiesService,
            EntityService entityService,
            EntityEditorFactory entityEditorFactory,
            AppConstants appConstants) {
        super(eventBus, view, proxy, VisualizerPresenter.SLOT_ENTITY_DETAILS);

        this.restDispatch = restDispatch;
        this.entitiesService = entitiesService;
        this.entityService = entityService;
        this.placeManager = placeManager;
        this.entityEditorFactory = entityEditorFactory;
        this.appConstants = appConstants;

        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(final PlaceRequest request) {
        super.prepareFromRequest(request);

        String encodedKeys = request.getParameter(UrlParameters.KEY, "");
        List<String> stringKeys = Splitter.on(",").splitToList(encodedKeys);

        if (stringKeys.size() == 1) {
            editEntity(stringKeys.get(0));
        } else if (!stringKeys.isEmpty()) {
            editEntities();
        }
    }

    @Override
    public boolean useManualReveal() {
        return true;
    }

    @Override
    public void save() {
        getView().clearErrors();

        if (currentEntities == null) {
            saveEntity();
        } else {
            saveEntities();
        }
    }

    @Override
    public void cancel() {
        revealEntitiesList();
    }

    @Override
    public void onPropertyEditorError(PropertyEditorErrorEvent event) {
        getView().showError(event.getError());
    }

    @ProxyEvent
    @Override
    public void onEditEntitiesSelected(EditEntitiesEvent event) {
        currentEntity = null;
        currentEntities = event.getParsedEntities();

        List<String> keys = FluentIterable.from(currentEntities)
                .transform(new Function<ParsedEntity, String>() {
                    @Override
                    public String apply(ParsedEntity input) {
                        return input.getKey().getEncodedKey();
                    }
                }).toList();

        String keysParam = Joiner.on(",").join(keys);

        placeManager.revealPlace(new PlaceRequest.Builder()
                .nameToken(NameTokens.editEntity)
                .with(UrlParameters.KEY, keysParam)
                .build());
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(PropertyEditorErrorEvent.getType(), this);
    }

    private void resetEditedEntities() {
        currentEntities = null;
        currentEntity = null;
    }

    private void saveEntity() {
        try {
            updateEntity();
        } catch (InvalidEntityFieldsException e) {
            getView().showErrorsTitle(appConstants.invalidFields());
        }
    }

    private void saveEntities() {
        try {
            updateEntities();
        } catch (InvalidEntityFieldsException e) {
            getView().showErrorsTitle(appConstants.invalidFields());
        }
    }

    private void onSaveEntityFailed(Throwable caught) {
        String message = caught.getMessage();
        if (message == null) {
            message = appConstants.errorSavingChanges();
        }
        getView().showError(message);
    }

    private void revealEntitiesList() {
        resetEditedEntities();

        PlaceRequest.Builder builder = new PlaceRequest.Builder(placeManager.getCurrentPlaceRequest())
                .nameToken(NameTokens.visualizer)
                .without(UrlParameters.KEY);

        placeManager.revealPlace(builder.build());
    }

    private void editEntities() {
        entitiesEditor = entityEditorFactory.create(currentEntities);
        setInSlot(EDITOR_SLOT, entitiesEditor);

        getProxy().manualReveal(this);
    }

    private void editEntity(String key) {
        String failureMessage = appConstants.failedGettingEntity();
        AsyncCallbackImpl<EntityDto> callback = new AsyncCallbackImpl<EntityDto>(failureMessage) {
            @Override
            public void onSuccess(EntityDto result) {
                currentEntity = new ParsedEntity(result);
                currentEntities = null;
                entityEditor = entityEditorFactory.create(currentEntity);

                setInSlot(EDITOR_SLOT, entityEditor);

                getProxy().manualReveal(EditEntityPresenter.this);
            }

            @Override
            public void handleFailure(Throwable caught) {
                super.handleFailure(caught);

                getProxy().manualRevealFailed();
            }
        };

        RestAction<EntityDto> getEntityAction = entityService.getEntity(key);

        restDispatch.execute(getEntityAction, callback);
    }

    private void onSaveEntitySucceeded(EntityDto newEntityDto) {
        EntitySavedEvent.fire(this, newEntityDto);
        DisplayMessageEvent.fire(this, new Message(appConstants.entitySaved(), MessageStyle.SUCCESS));
        revealEntitiesList();
    }

    private void onSaveEntitiesSucceeded(List<EntityDto> entities) {
        DisplayMessageEvent.fire(this, new Message(appConstants.entitiesSaved(), MessageStyle.SUCCESS));
        EntitiesSavedEvent.fire(this, entities);
        resetEditedEntities();
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
                }
        );
    }

    private void updateEntities() {
        List<EntityDto> entities = entitiesEditor.flush();

        restDispatch.execute(entitiesService.updateEntities(entities),
                new AsyncCallbackImpl<List<EntityDto>>() {
                    @Override
                    public void handleFailure(Throwable caught) {
                        onSaveEntityFailed(caught);
                    }

                    @Override
                    public void onSuccess(List<EntityDto> entities) {
                        onSaveEntitiesSucceeded(entities);
                    }
                }
        );
    }
}
