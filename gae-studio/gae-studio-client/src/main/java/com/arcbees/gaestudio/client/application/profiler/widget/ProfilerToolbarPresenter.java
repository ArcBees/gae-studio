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
import com.arcbees.gaestudio.client.rest.RecordService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class ProfilerToolbarPresenter extends PresenterWidget<ProfilerToolbarPresenter.MyView> implements
        ProfilerToolbarUiHandlers {
    interface MyView extends View, HasUiHandlers<ProfilerToolbarUiHandlers> {
        void setRecordingState(Boolean isRecording);
    }

    private final RecordService recordService;
    private final RestDispatch restDispatch;

    @Inject
    ProfilerToolbarPresenter(EventBus eventBus,
                             MyView view,
                             RestDispatch restDispatch,
                             RecordService recordService) {
        super(eventBus, view);

        this.restDispatch = restDispatch;
        this.recordService = recordService;

        getView().setUiHandlers(this);
    }

    @Override
    public void onToggleRecording(Boolean start) {
        AsyncCallback<Long> callback = getRecordingCallback(start);

        RestAction<Long> action;
        if (start) {
            action = recordService.startRecording();
        } else {
            action = recordService.stopRecording();
        }

        restDispatch.execute(action, callback);
    }

    @Override
    public void clearOperationRecords() {
        ClearOperationRecordsEvent.fire(this);
    }

    private AsyncCallback<Long> getRecordingCallback(final boolean start) {
        return new AsyncCallbackImpl<Long>() {
            @Override
            public void onFailure(Throwable caught) {
                getView().setRecordingState(!start);
            }

            @Override
            public void onSuccess(Long result) {
                getView().setRecordingState(start);
                RecordingStateChangedEvent.fire(ProfilerToolbarPresenter.this, start, result);
            }
        };
    }
}

