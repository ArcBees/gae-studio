/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.header;

import com.arcbees.gaestudio.client.application.event.RecordingToggledEvent;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class HeaderPresenter extends PresenterWidget<HeaderPresenter.MyView> implements HeaderUiHandlers{

    public interface MyView extends View, HasUiHandlers<HeaderUiHandlers> {
        void setRecordingState(boolean start);
    }

    @Inject
    public HeaderPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    public void onToggleRecording(boolean start) {
        RecordingToggledEvent.fire(this, start);
    }

    @Override
    protected void onBind() {
        super.onBind();

        setDefaultRecordingState();
    }

    private void setDefaultRecordingState() {
        onToggleRecording(false);
        getView().setRecordingState(false);
    }
}
