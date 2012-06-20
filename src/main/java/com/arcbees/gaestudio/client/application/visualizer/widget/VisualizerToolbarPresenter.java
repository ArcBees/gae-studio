/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.event.KindSelectedEvent;
import com.arcbees.gaestudio.client.application.visualizer.event.RefreshEntitiesEvent;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class VisualizerToolbarPresenter extends PresenterWidget<VisualizerToolbarPresenter.MyView> implements
        VisualizerToolbarUiHandlers, KindSelectedEvent.KindSelectedHandler{

    public interface MyView extends View, HasUiHandlers<VisualizerToolbarUiHandlers> {
        void setKindSelected(boolean isSelected);
    }

    public static final Object TYPE_SetKindsContent = new Object();

    private KindListPresenter kindListPresenter;

    @Inject
    public VisualizerToolbarPresenter(final EventBus eventBus, final MyView view,
                                      final KindListPresenter kindListPresenter) {
        super(eventBus, view);

        this.kindListPresenter = kindListPresenter;
    }

    @Override
    public void refresh() {
        RefreshEntitiesEvent.fire(this);
    }

    @Override
    public void onKindSelected(KindSelectedEvent event) {
        String kind = event.getKind();
        getView().setKindSelected(!kind.isEmpty());
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(TYPE_SetKindsContent, kindListPresenter);

        addRegisteredHandler(KindSelectedEvent.getType(), this);
    }
}

