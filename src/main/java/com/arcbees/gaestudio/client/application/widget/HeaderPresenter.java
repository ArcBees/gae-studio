/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.widget;

import com.arcbees.gaestudio.client.application.profiler.event.RecordingStateChangedEvent;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingAction;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class HeaderPresenter extends PresenterWidget<HeaderPresenter.MyView> implements HeaderUiHandlers {

    public interface MyView extends View, HasUiHandlers<HeaderUiHandlers> {
        void setPending(Boolean pending);

        void setRecordingState(Boolean isRecording);
    }

    private final DispatchAsync dispatcher;

    @Inject
    public HeaderPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);

        this.dispatcher = dispatcher;
    }

    @Override
    public void onToggleRecording(final Boolean start) {
        getView().setPending(true);
            dispatcher.execute(new SetRecordingAction(start), new AsyncCallback<SetRecordingResult>() {
                @Override
                public void onFailure(Throwable caught) {
                    getView().setRecordingState(!start);
                }

                @Override
                public void onSuccess(SetRecordingResult result) {
                    getView().setRecordingState(start);
                    RecordingStateChangedEvent.fire(HeaderPresenter.this, start, result.getCurrentRecordId());
                }
            });
    }
}
