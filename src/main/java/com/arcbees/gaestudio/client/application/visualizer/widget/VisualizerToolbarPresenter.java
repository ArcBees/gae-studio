/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.visualizer.event.RefreshEntitiesEvent;
import com.arcbees.gaestudio.client.application.visualizer.widget.kind.KindListPresenter;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class VisualizerToolbarPresenter extends PresenterWidget<VisualizerToolbarPresenter.MyView> implements
        VisualizerToolbarUiHandlers {

    public interface MyView extends View, HasUiHandlers<VisualizerToolbarUiHandlers> {
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
    protected void onBind() {
        super.onBind();

        setInSlot(TYPE_SetKindsContent, kindListPresenter);
    }
}

