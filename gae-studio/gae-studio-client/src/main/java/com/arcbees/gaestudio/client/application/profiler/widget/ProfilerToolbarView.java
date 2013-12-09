/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.application.ui.ToolbarButtonCallback;
import com.arcbees.gaestudio.client.application.ui.UiFactory;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.util.DebugIds;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ProfilerToolbarView extends ViewWithUiHandlers<ProfilerToolbarUiHandlers> implements
        ProfilerToolbarPresenter.MyView {
    interface Binder extends UiBinder<Widget, ProfilerToolbarView> {
    }

    @UiField(provided = true)
    AppResources resources;
    @UiField
    HTMLPanel buttons;

    private final UiFactory uiFactory;
    private final AppConstants myConstants;
    private final ToolbarButton record;
    private final ToolbarButton stop;
    private final ToolbarButton clear;
    private Boolean isRecording = false;

    @Inject
    ProfilerToolbarView(Binder uiBinder,
                        AppResources resources,
                        UiFactory uiFactory,
                        AppConstants myConstants) {
        this.resources = resources;
        this.uiFactory = uiFactory;
        this.myConstants = myConstants;

        initWidget(uiBinder.createAndBindUi(this));

        stopRecordindWhenWindowIsClosed();

        record = createRecordButton();
        stop = createStopButton();
        clear = createClearButton();

        buttons.add(record);
        buttons.add(stop);
        buttons.add(clear);

        setRecordingState(false);
    }

    @Override
    public void setRecordingState(Boolean isRecording) {
        this.isRecording = isRecording;
        record.setEnabled(!isRecording);
        stop.setEnabled(isRecording);
        clear.setEnabled(!isRecording);
    }

    private void stopRecordindWhenWindowIsClosed() {
        Window.addCloseHandler(new CloseHandler<Window>() {
            @Override
            public void onClose(CloseEvent<Window> windowCloseEvent) {
                if (isRecording) {
                    getUiHandlers().onToggleRecording(false);
                }
            }
        });
    }

    private ToolbarButton createRecordButton() {
        return uiFactory.createToolbarButton(myConstants.record(), resources.record(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                isRecording = true;
                getUiHandlers().onToggleRecording(true);
            }
        }, DebugIds.RECORD);
    }

    private ToolbarButton createStopButton() {
        return uiFactory.createToolbarButton(myConstants.stop(), resources.stop(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                isRecording = false;
                getUiHandlers().onToggleRecording(false);
            }
        });
    }

    private ToolbarButton createClearButton() {
        return uiFactory.createToolbarButton(myConstants.clear(), resources.delete(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                getUiHandlers().clearOperationRecords();
            }
        });
    }
}
