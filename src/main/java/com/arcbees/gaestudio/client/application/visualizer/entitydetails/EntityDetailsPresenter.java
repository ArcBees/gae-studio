/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.entitydetails;

import com.arcbees.gaestudio.client.application.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
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
        implements EntitySelectedEvent.EntitySelectedHandler, EntityDetailsUiHandlers {

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
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(EntitySelectedEvent.getType(), this);
    }

    @Override
    public void onEntitySelected(EntitySelectedEvent event) {
        currentParsedEntity = event.getParsedEntity();
        getView().displayEntityDetails(currentParsedEntity.getJson());
    }

    @Override
    public void editEntity(String json) {
        EntityDTO entityDTO = currentParsedEntity.getEntityDTO();
        entityDTO.setJson(json);
        dispatcher.execute(new UpdateEntityAction(entityDTO), new AsyncCallback<UpdateEntityResult>() {
            @Override
            public void onFailure(Throwable caught) {
                onEditFailed(caught);
            }

            @Override
            public void onSuccess(UpdateEntityResult result) {
                currentParsedEntity.parseJson();
                getView().hide();
            }
        });
    }

    private void onEditFailed(Throwable caught) {
        String message = caught.getMessage();
        if (message == null) {
            message = "Unable to save the changes in the datastore";
        }
        getView().showError(message);
    }

}
