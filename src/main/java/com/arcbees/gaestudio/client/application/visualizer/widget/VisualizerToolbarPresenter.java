/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.event.EntitySelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.RefreshEntitiesEvent;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dispatch.GetEmptyKindEntityAction;
import com.arcbees.gaestudio.shared.dispatch.GetEmptyKindEntityResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class VisualizerToolbarPresenter extends PresenterWidget<VisualizerToolbarPresenter.MyView> implements
        VisualizerToolbarUiHandlers, KindSelectedEvent.KindSelectedHandler {

    public interface MyView extends View, HasUiHandlers<VisualizerToolbarUiHandlers> {
        void setKindSelected(boolean isSelected);
    }

    public static final Object TYPE_SetKindsContent = new Object();

    private final DispatchAsync dispatcher;
    private final KindListPresenter kindListPresenter;
    private String currentKind = "";

    @Inject
    public VisualizerToolbarPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
                                      final KindListPresenter kindListPresenter) {
        super(eventBus, view);

        this.dispatcher = dispatcher;
        this.kindListPresenter = kindListPresenter;
    }

    @Override
    public void refresh() {
        RefreshEntitiesEvent.fire(this);
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        currentKind = event.getKind();
        getView().setKindSelected(!currentKind.isEmpty());
    }

    @Override
    public void create() {
        if (!currentKind.isEmpty()) {
            dispatcher.execute(new GetEmptyKindEntityAction(currentKind), new AsyncCallbackImpl
                    <GetEmptyKindEntityResult>() {
                @Override
                public void onSuccess(GetEmptyKindEntityResult result) {
                    EntityDTO emptyEntityDto = result.getEntityDTO();
                    EntitySelectedEvent.fire(VisualizerToolbarPresenter.this, new ParsedEntity(emptyEntityDto));
                }
            });
        }
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(TYPE_SetKindsContent, kindListPresenter);

        addRegisteredHandler(KindSelectedEvent.getType(), this);
    }
}

