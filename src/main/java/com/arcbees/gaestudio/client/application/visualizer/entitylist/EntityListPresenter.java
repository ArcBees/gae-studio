/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.gaestudio.client.application.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.event.KindSelectedEvent;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.arcbees.gaestudio.shared.dto.entity.KeyDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.ArrayList;

public class EntityListPresenter extends PresenterWidget<EntityListPresenter.MyView>
        implements KindSelectedEvent.KindSelectedHandler, EntityListUiHandlers {

    public interface MyView extends View, HasUiHandlers<EntityListUiHandlers> {
        void displayEntities(ArrayList<EntityDTO> entities);
    }
    
    private final DispatchAsync dispatcher;

    @Inject
    public EntityListPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);

        this.dispatcher = dispatcher;
    }

    @Override
    protected void onBind() {
        super.onBind();
        addRegisteredHandler(KindSelectedEvent.getType(), this);
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        dispatcher.execute(
                new GetEntitiesByKindAction(event.getKind()),
                new AsyncCallback<GetEntitiesByKindResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        // TODO implement
                    }

                    @Override
                    public void onSuccess(GetEntitiesByKindResult result) {
                        getView().displayEntities(result.getEntities());
                    }
                });
    }

    @Override
    public void onEntityClicked(KeyDTO entityKey, String entityData) {
        getEventBus().fireEvent(new EntitySelectedEvent(entityKey, entityData));
    }

}
