/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class DetailsPresenter extends PresenterWidget<DetailsPresenter.MyView> {

    public interface MyView extends View {
    }

    private final DispatchAsync dispatcher;

    @Inject
    public DetailsPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);
        this.dispatcher = dispatcher;
    }
    
    @Override
    protected void onBind() {
        super.onBind();
    }

}
