/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
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
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class EntityDetailsPresenter extends PresenterWidget<EntityDetailsPresenter.MyView>
        implements EditEntityEvent.EditEntityHandler, EntityDetailsUiHandlers {

    public interface MyView extends View, HasUiHandlers<EntityDetailsUiHandlers> {
        void displayEntityDetails(String json);

        void hide();

        void showError(String message);
    }

    private final DispatchAsync dispatcher;
    private ParsedEntity currentParsedEntity;

    @Inject
    public EntityDetailsPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);

        this.dispatcher = dispatcher;
    }

    @Override
    public void onEditEntity(EditEntityEvent event) {
        currentParsedEntity = event.getParsedEntity();
        getView().displayEntityDetails(currentParsedEntity.getJson());
    }

    @Override
    public void saveEntity(String json) {
        EntityDTO entityDTO = currentParsedEntity.getEntityDTO();
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
            message = "Unable to save the changes in the datastore.";
        }
        getView().showError(message);
    }

    private void onSaveEntitySucceeded(UpdateEntityResult result) {
        EntityDTO newEntityDto = result.getResult();
        EntitySavedEvent.fire(this, newEntityDto);
        Message message = new Message("Entity saved.", MessageStyle.SUCCESS);
        DisplayMessageEvent.fire(this, message);
        getView().hide();
    }

}
