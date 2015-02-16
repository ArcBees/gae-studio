/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.Set;

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
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
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

        void displayEntitiesDeletion(DeleteEntities deleteType, String kind, String namespace,
                Set<ParsedEntity> entities);

        void hide();
    }

    enum DeleteType {
        SINGLE,
        BATCH
    }

    private final AppConstants myConstants;
    private final EntitiesService entitiesService;
    private final EntityService entityService;
    private final RestDispatch restDispatch;

    private DeleteType lastEvent;
    private ParsedEntity currentParsedEntity;
    private DeleteEntitiesEvent deleteEntitiesEvent;

    @Inject
    EntityDeletionPresenter(
            EventBus eventBus,
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
                deleteEntitiesEvent.getKind(), deleteEntitiesEvent.getNamespace(), deleteEntitiesEvent.getEntities());

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
        Set<String> encodedKeys = FluentIterable.from(deleteEntitiesEvent.getEntities())
                .transform(new Function<ParsedEntity, String>() {
                    @Override
                    public String apply(ParsedEntity input) {
                        return input.getKey().getEncodedKey();
                    }
                }).toSet();

        restDispatch.execute(entitiesService.deleteAll(deleteEntitiesEvent.getKind(),
                        deleteEntitiesEvent.getNamespace(),
                        deleteEntitiesEvent.getDeleteEntities(),
                        encodedKeys),
                new AsyncCallbackImpl<Void>(myConstants.errorEntityDelete()) {
                    @Override
                    public void onSuccess(Void result) {
                        onEntitiesDeletedSuccess();
                    }
                });
    }

    private void deleteSingleEntity() {
        if (currentParsedEntity != null) {
            final EntityDto entityDto = currentParsedEntity.getEntityDto();
            KeyDto key = entityDto.getKey();

            restDispatch.execute(entityService.deleteEntity(key.getEncodedKey()),
                    new AsyncCallbackImpl<Void>(myConstants.errorEntityDelete()) {
                        @Override
                        public void onSuccess(Void result) {
                            onEntityDeletedSuccess(entityDto);
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
