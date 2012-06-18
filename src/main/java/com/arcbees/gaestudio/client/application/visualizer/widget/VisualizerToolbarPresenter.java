/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class VisualizerToolbarPresenter extends PresenterWidget<VisualizerToolbarPresenter.MyView> implements
        VisualizerToolbarUiHandlers {

    public interface MyView extends View, HasUiHandlers<VisualizerToolbarUiHandlers> {
    }

    private final DispatchAsync dispatcher;

    @Inject
    public VisualizerToolbarPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);

        this.dispatcher = dispatcher;
    }
}
