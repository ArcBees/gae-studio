/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

import org.fusesource.restygwt.client.MethodCallback;

import com.arcbees.gaestudio.client.application.profiler.event.ClearOperationRecordsEvent;
import com.arcbees.gaestudio.client.application.profiler.event.RecordingStateChangedEvent;
import com.arcbees.gaestudio.client.rest.RecordService;
import com.arcbees.gaestudio.client.util.MethodCallbackImpl;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class ProfilerToolbarPresenter extends PresenterWidget<ProfilerToolbarPresenter.MyView> implements
        ProfilerToolbarUiHandlers {
    interface MyView extends View, HasUiHandlers<ProfilerToolbarUiHandlers> {
        void setRecordingState(Boolean isRecording);
    }

    private final RecordService recordService;

    @Inject
    ProfilerToolbarPresenter(EventBus eventBus,
                             MyView view,
                             RecordService recordService) {
        super(eventBus, view);

        getView().setUiHandlers(this);

        this.recordService = recordService;
    }

    @Override
    public void onToggleRecording(Boolean start) {
        MethodCallback<Long> methodCallback = getRecordingCallback(start);

        if (start) {
            recordService.startRecording(methodCallback);
        } else {
            recordService.stopRecording(methodCallback);
        }
    }

    @Override
    public void clearOperationRecords() {
        ClearOperationRecordsEvent.fire(this);
    }

    private MethodCallback<Long> getRecordingCallback(final boolean start) {
        return new MethodCallbackImpl<Long>() {
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

