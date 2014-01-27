/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.EditEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityEditorFactory;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityEditorPresenter;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.InvalidEntityFieldsException;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyEditorErrorEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.rest.EntityService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class EntityDetailsPresenter extends PresenterWidget<EntityDetailsPresenter.MyView>
        implements EditEntityEvent.EditEntityHandler, EntityDetailsUiHandlers,
        PropertyEditorErrorEvent.PropertyEditorErrorHandler {
    interface MyView extends View, HasUiHandlers<EntityDetailsUiHandlers> {
        void displayEntityDetails();

        void hide();

        void showError(String message);

        void clearErrors();

        void showErrorsTitle(String title);
    }

    public static final Object EDITOR_SLOT = new Object();

    private final RestDispatch restDispatch;
    private final EntityService entityService;
    private final EntityEditorFactory entityEditorFactory;
    private final AppConstants appConstants;

    private EntityEditorPresenter entityEditor;

    @Inject
    EntityDetailsPresenter(EventBus eventBus,
                           MyView view,
                           RestDispatch restDispatch,
                           EntityService entityService,
                           EntityEditorFactory entityEditorFactory,
                           AppConstants appConstants) {
        super(eventBus, view);

        this.restDispatch = restDispatch;
        this.entityService = entityService;
        this.entityEditorFactory = entityEditorFactory;
        this.appConstants = appConstants;

        getView().setUiHandlers(this);
    }

    @Override
    public void onEditEntity(EditEntityEvent event) {
        ParsedEntity parsedEntity = event.getParsedEntity();
        entityEditor = entityEditorFactory.create(parsedEntity);

        setInSlot(EDITOR_SLOT, entityEditor);
        getView().displayEntityDetails();
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
    public void onPropertyEditorError(PropertyEditorErrorEvent event) {
        getView().showError(event.getError());
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(EditEntityEvent.getType(), this);
        addRegisteredHandler(PropertyEditorErrorEvent.getType(), this);
    }

    private void onSaveEntityFailed(Throwable caught) {
        String message = caught.getMessage();
        if (message == null) {
            message = "Unable to save the changes in the datastore.";
        }
        getView().showError(message);
    }

    private void onSaveEntitySucceeded(EntityDto newEntityDto) {
        EntitySavedEvent.fire(this, newEntityDto);
        Message message = new Message("Entity saved.", MessageStyle.SUCCESS);
        DisplayMessageEvent.fire(this, message);
        getView().hide();
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
