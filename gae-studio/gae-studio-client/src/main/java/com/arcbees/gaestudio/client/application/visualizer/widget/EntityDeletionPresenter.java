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
import com.arcbees.gaestudio.client.rest.EntitiesService;
import com.arcbees.gaestudio.client.rest.EntityService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import static com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntitiesEvent.DeleteEntitiesHandler;
import static com.arcbees.gaestudio.client.application.visualizer.widget.EntityDeletionPresenter.DeleteType.BATCH;
import static com.arcbees.gaestudio.client.application.visualizer.widget.EntityDeletionPresenter.DeleteType.SINGLE;

public class EntityDeletionPresenter extends PresenterWidget<EntityDeletionPresenter.MyView>
        implements DeleteEntityEvent.DeleteEntityHandler, EntityDeletionUiHandlers, DeleteEntitiesHandler {
    private final RestDispatch restDispatch;

    interface MyView extends View, HasUiHandlers<EntityDeletionUiHandlers> {
        void displayEntityDeletion(ParsedEntity parsedEntity);

        void displayEntitiesDeletion(DeleteEntities deleteType, String kind, String namespace);

        void hide();
    }

    enum DeleteType {
        SINGLE,
        BATCH
    }

    private final AppConstants myConstants;
    private final EntitiesService entitiesService;
    private final EntityService entityService;

    private DeleteType lastEvent;
    private ParsedEntity currentParsedEntity;
    private DeleteEntitiesEvent deleteEntitiesEvent;

    @Inject
    EntityDeletionPresenter(EventBus eventBus,
                            MyView view,
                            AppConstants myConstants,
                            RestDispatch restDispatch,
                            EntitiesService entitiesService,
                            EntityService entityService) {
        super(eventBus, view);

        getView().setUiHandlers(this);

        this.restDispatch = restDispatch;
        this.entitiesService = entitiesService;
        this.myConstants = myConstants;
        this.entityService = entityService;
    }

    @Override
    public void onDeleteEntity(DeleteEntityEvent event) {
        currentParsedEntity = event.getParsedEntity();
        getView().displayEntityDeletion(currentParsedEntity);
        lastEvent = SINGLE;
    }

    @Override
    public void onDeleteEntities(DeleteEntitiesEvent event) {
        deleteEntitiesEvent = event;

        getView().displayEntitiesDeletion(deleteEntitiesEvent.getDeleteEntities(),
                deleteEntitiesEvent.getKind(), deleteEntitiesEvent.getNamespace());

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
        restDispatch.execute(entitiesService.deleteAll(deleteEntitiesEvent.getKind(),
                deleteEntitiesEvent.getNamespace(),
                deleteEntitiesEvent.getDeleteEntities()),
                new AsyncCallbackImpl<Void>() {
                    @Override
                    public void onSuccess(Void result) {
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
            final EntityDto entityDto = currentParsedEntity.getEntityDto();
            KeyDto key = entityDto.getKey();

            restDispatch.execute(entityService.deleteEntity(key.getKind(), key.getName(), key.getId()),
                    new AsyncCallbackImpl<Void>() {
                        @Override
                        public void onSuccess(Void result) {
                            onEntityDeletedSuccess(entityDto);
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
