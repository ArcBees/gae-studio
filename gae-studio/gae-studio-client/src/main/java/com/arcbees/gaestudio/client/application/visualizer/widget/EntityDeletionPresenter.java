/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntitiesEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitiesDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesAction;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesResult;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesType;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityAction;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import static com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntitiesEvent.DeleteEntitiesHandler;
import static com.arcbees.gaestudio.client.application.visualizer.widget.EntityDeletionPresenter.DeleteType.BATCH;
import static com.arcbees.gaestudio.client.application.visualizer.widget.EntityDeletionPresenter.DeleteType.SINGLE;

public class EntityDeletionPresenter extends PresenterWidget<EntityDeletionPresenter.MyView>
        implements DeleteEntityEvent.DeleteEntityHandler, EntityDeletionUiHandlers, DeleteEntitiesHandler {
    interface MyView extends View, HasUiHandlers<EntityDeletionUiHandlers> {
        void displayEntityDeletion(ParsedEntity parsedEntity);

        void displayEntitiesDeletion(DeleteEntitiesType deleteType, String value);

        void hide();
    }

    enum DeleteType {
        SINGLE,
        BATCH
    }

    private final DispatchAsync dispatcher;
    private final AppConstants myConstants;

    private DeleteType lastEvent;
    private ParsedEntity currentParsedEntity;
    private DeleteEntitiesType deleteType;
    private String deleteTypeValue;

    @Inject
    EntityDeletionPresenter(EventBus eventBus,
                            MyView view,
                            DispatchAsync dispatcher,
                            AppConstants myConstants) {
        super(eventBus, view);

        getView().setUiHandlers(this);

        this.dispatcher = dispatcher;
        this.myConstants = myConstants;
    }

    @Override
    public void onDeleteEntity(DeleteEntityEvent event) {
        currentParsedEntity = event.getParsedEntity();
        getView().displayEntityDeletion(currentParsedEntity);
        lastEvent = SINGLE;
    }

    @Override
    public void onDeleteEntities(DeleteEntitiesEvent event) {
        deleteType = event.getDeleteType();
        deleteTypeValue = event.getValue();

        getView().displayEntitiesDeletion(deleteType, deleteTypeValue);

        lastEvent = BATCH;
    }

    @Override
    public void reset() {
        lastEvent = null;
    }

    @Override
    public void deleteEntity() {
        if (SINGLE.equals(lastEvent)) {
            deleteSingleEntity();
        } else if (BATCH.equals(lastEvent)) {
            deleteEntities();
        }

        reset();
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(DeleteEntityEvent.getType(), this);
        addRegisteredHandler(DeleteEntitiesEvent.getType(), this);
    }

    private void deleteEntities() {
        dispatcher.execute(DeleteEntitiesAction.create(deleteType, deleteTypeValue),
                new AsyncCallback<DeleteEntitiesResult>() {
                    @Override
                    public void onSuccess(DeleteEntitiesResult result) {
                        onEntitiesDeletedSuccess();
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        showMessage(myConstants.errorEntityDelete(), MessageStyle.ERROR);
                    }
                });
    }

    private void deleteSingleEntity() {
        if (currentParsedEntity != null) {
            final EntityDto entityDTO = currentParsedEntity.getEntityDTO();
            dispatcher.execute(new DeleteEntityAction(entityDTO), new AsyncCallback<DeleteEntityResult>() {
                @Override
                public void onSuccess(DeleteEntityResult result) {
                    onEntityDeletedSuccess(entityDTO);
                }

                @Override
                public void onFailure(Throwable caught) {
                    showMessage(myConstants.errorEntityDelete(), MessageStyle.ERROR);
                }
            });
        }
    }

    private void onEntityDeletedSuccess(EntityDto entityDTO) {
        showMessage(myConstants.successEntityDelete(), MessageStyle.SUCCESS);
        EntityDeletedEvent.fire(this, entityDTO);
    }

    private void onEntitiesDeletedSuccess() {
        showMessage(myConstants.successEntitiesDelete(), MessageStyle.SUCCESS);
        EntitiesDeletedEvent.fire(this);
    }

    private void showMessage(String content, MessageStyle style) {
        Message message = new Message(content, style);
        DisplayMessageEvent.fire(this, message);
    }
}
