/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.gaestudio.client.application.profiler.event.ClearOperationRecordsEvent;
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

public class ProfilerToolbarPresenter extends PresenterWidget<ProfilerToolbarPresenter.MyView> implements
        ProfilerToolbarUiHandlers {
    interface MyView extends View, HasUiHandlers<ProfilerToolbarUiHandlers> {
        void setRecordingState(Boolean isRecording);
    }

    private final DispatchAsync dispatcher;

    @Inject
    ProfilerToolbarPresenter(EventBus eventBus,
                             MyView view,
                             DispatchAsync dispatcher) {
        super(eventBus, view);

        getView().setUiHandlers(this);

        this.dispatcher = dispatcher;
    }

    @Override
    public void onToggleRecording(final Boolean start) {
        dispatcher.execute(new SetRecordingAction(start), new AsyncCallback<SetRecordingResult>() {
            @Override
            public void onFailure(Throwable caught) {
                getView().setRecordingState(!start);
            }

            @Override
            public void onSuccess(SetRecordingResult result) {
                getView().setRecordingState(start);
                RecordingStateChangedEvent.fire(ProfilerToolbarPresenter.this, start, result.getCurrentRecordId());
            }
        });
    }

    @Override
    public void clearOperationRecords() {
        ClearOperationRecordsEvent.fire(this);
    }
}

