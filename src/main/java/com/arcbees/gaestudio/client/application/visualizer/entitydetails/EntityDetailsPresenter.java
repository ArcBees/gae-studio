/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.entitydetails;

import com.arcbees.gaestudio.client.application.event.EntitySelectedEvent;
import com.arcbees.gaestudio.shared.dto.entity.KeyDTO;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class EntityDetailsPresenter extends PresenterWidget<EntityDetailsPresenter.MyView>
        implements EntitySelectedEvent.EntitySelectedHandler, EntityDetailsUiHandlers {

    public interface MyView extends View, HasUiHandlers<EntityDetailsUiHandlers> {
        void displayEntityDetails(KeyDTO entityKey, String entityData);
    }

    @Inject
    public EntityDetailsPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    protected void onBind() {
        super.onBind();
        addRegisteredHandler(EntitySelectedEvent.getType(), this);
    }

    @Override
    public void onEntitySelected(EntitySelectedEvent event) {
        getView().displayEntityDetails(event.getEntityKey(), event.getEntityData());
    }

}
