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
import com.arcbees.gaestudio.client.application.visualizer.event.DeleteEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EditEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntityDeletedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityAction;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityResult;
import com.arcbees.gaestudio.shared.dispatch.UpdateEntityAction;
import com.arcbees.gaestudio.shared.dispatch.UpdateEntityResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class EntityDeletionPresenter extends PresenterWidget<EntityDeletionPresenter.MyView>
        implements DeleteEntityEvent.DeleteEntityHandler, EntityDeletionUiHandlers {

    interface MyView extends View, HasUiHandlers<EntityDeletionUiHandlers> {
        void displayEntityDeletion(ParsedEntity p);

        void hide();
    }

    private final DispatchAsync dispatcher;
    private final AppConstants myConstants;
    private ParsedEntity currentParsedEntity;

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
    }

    @Override
    public void deleteEntity() {
                if (currentParsedEntity != null) {
            final EntityDto entityDTO = currentParsedEntity.getEntityDTO();
            dispatcher.execute(new DeleteEntityAction(entityDTO), new AsyncCallback<DeleteEntityResult>() {
                @Override
                public void onSuccess(DeleteEntityResult result) {
                    onEntityDeletedSuccess(entityDTO);
                }

                @Override
                public void onFailure(Throwable caught) {
                    Message message = new Message(myConstants.errorEntityDelete(), MessageStyle.ERROR);
                    DisplayMessageEvent.fire(EntityDeletionPresenter.this, message);
                }
            });
        }
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(DeleteEntityEvent.getType(), this);
    }

    private void onEntityDeletedSuccess(EntityDto entityDTO) {
        Message message = new Message(myConstants.successEntityDelete(), MessageStyle.SUCCESS);
        DisplayMessageEvent.fire(this, message);
        EntityDeletedEvent.fire(this, entityDTO);
    }
}
