/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.header;

import com.arcbees.gaestudio.client.application.event.RecordingToggledEvent;
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
    }

    private final DispatchAsync dispatcher;
    private Boolean isPending = false;

    @Inject
    public HeaderPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);
        this.dispatcher = dispatcher;
    }

    @Override
    public void onToggleRecording(final Boolean start) {
        if (!isPending) {
            getView().setPending(true);
            dispatcher.execute(new SetRecordingAction(start), new AsyncCallback<SetRecordingResult>() {
                @Override
                public void onFailure(Throwable caught) {
                    getView().setPending(false);
                    // TODO: Manage error
                }

                @Override
                public void onSuccess(SetRecordingResult result) {
                    getView().setPending(false);
                    RecordingToggledEvent.fire(HeaderPresenter.this, start);
                }
            });
        }
    }
}
