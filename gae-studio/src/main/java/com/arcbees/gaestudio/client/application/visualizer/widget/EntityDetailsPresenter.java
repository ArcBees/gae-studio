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
import com.arcbees.gaestudio.client.application.visualizer.event.EditEntityEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySavedEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
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

public class EntityDetailsPresenter extends PresenterWidget<EntityDetailsPresenter.MyView>
        implements EditEntityEvent.EditEntityHandler, EntityDetailsUiHandlers {
    interface MyView extends View, HasUiHandlers<EntityDetailsUiHandlers> {
        void displayEntityDetails(String json);

        void hide();

        void showError(String message);
    }

    private final DispatchAsync dispatcher;
    private ParsedEntity currentParsedEntity;

    @Inject
    EntityDetailsPresenter(EventBus eventBus,
                           MyView view,
                           DispatchAsync dispatcher) {
        super(eventBus, view);

        getView().setUiHandlers(this);

        this.dispatcher = dispatcher;
    }

    @Override
    public void onEditEntity(EditEntityEvent event) {
        currentParsedEntity = event.getParsedEntity();
        getView().displayEntityDetails(currentParsedEntity.getJson());
    }

    @Override
    public void saveEntity(String json) {
        EntityDto entityDTO = currentParsedEntity.getEntityDTO();
        entityDTO.setJson(json);
        dispatcher.execute(new UpdateEntityAction(entityDTO), new AsyncCallback<UpdateEntityResult>() {
            @Override
            public void onFailure(Throwable caught) {
                onSaveEntityFailed(caught);
            }

            @Override
            public void onSuccess(UpdateEntityResult result) {
                onSaveEntitySucceeded(result);
            }
        });
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(EditEntityEvent.getType(), this);
    }

    private void onSaveEntityFailed(Throwable caught) {
        String message = caught.getMessage();
        if (message == null) {
            message = "Unable to delete the changes in the datastore.";
        }
        getView().showError(message);
    }

    private void onSaveEntitySucceeded(UpdateEntityResult result) {
        EntityDto newEntityDto = result.getResult();
        EntitySavedEvent.fire(this, newEntityDto);
        Message message = new Message("Entity saved.", MessageStyle.SUCCESS);
        DisplayMessageEvent.fire(this, message);
        getView().hide();
    }
}
