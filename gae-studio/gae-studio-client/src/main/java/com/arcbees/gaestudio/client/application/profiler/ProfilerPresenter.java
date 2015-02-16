/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.client.application.profiler;

import java.util.logging.Logger;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.channel.ChannelHandler;
import com.arcbees.gaestudio.client.application.event.DbOperationEvent;
import com.arcbees.gaestudio.client.application.profiler.event.ClearOperationRecordsEvent;
import com.arcbees.gaestudio.client.application.profiler.event.RecordingStateChangedEvent;
import com.arcbees.gaestudio.client.application.profiler.widget.StatementPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.StatisticsPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.ToolbarPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.FiltersPresenter;
import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.application.ui.ToolbarButtonCallback;
import com.arcbees.gaestudio.client.application.ui.UiFactory;
import com.arcbees.gaestudio.client.debug.DebugIds;
import com.arcbees.gaestudio.client.debug.DebugLogMessages;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.FontsResources;
import com.arcbees.gaestudio.client.rest.RecordService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.common.collect.Lists;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class ProfilerPresenter extends Presenter<ProfilerPresenter.MyView, ProfilerPresenter.MyProxy>
        implements RecordingStateChangedEvent.RecordingStateChangedHandler, DbOperationEvent.DbOperationHandler {
    interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.profiler)
    interface MyProxy extends ProxyPlace<ProfilerPresenter> {
    }

    public static final Object SLOT_REQUESTS = new Object();
    public static final Object SLOT_STATISTICS = new Object();
    public static final Object SLOT_STATETEMENTS = new Object();
    public static final Object SLOT_TOOLBAR = new Object();

    private final RestDispatch restDispatch;
    private final FiltersPresenter filterPresenter;
    private final StatisticsPresenter statisticsPresenter;
    private final StatementPresenter statementPresenter;
    private final Logger logger;
    private final UiFactory uiFactory;
    private final AppConstants myConstants;
    private final AppResources resources;
    private final RecordService recordService;
    private final FontsResources fontsResources;
    private final ChannelHandler channelHandler;
    private final ToolbarPresenter toolbarPresenter;
    private final ToolbarButton record;
    private final ToolbarButton stop;
    private final ToolbarButton clear;

    private Boolean isRecording = false;

    @Inject
    ProfilerPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            RestDispatch restDispatch,
            FiltersPresenter filterPresenter,
            StatisticsPresenter statisticsPresenter,
            StatementPresenter statementPresenter,
            Logger logger,
            UiFactory uiFactory,
            AppConstants myConstants,
            AppResources resources,
            FontsResources fontsResources,
            RecordService recordService,
            ChannelHandler channelHandler,
            ToolbarPresenter toolbarPresenter) {
        super(eventBus, view, proxy);

        this.restDispatch = restDispatch;
        this.filterPresenter = filterPresenter;
        this.statisticsPresenter = statisticsPresenter;
        this.statementPresenter = statementPresenter;
        this.fontsResources = fontsResources;
        this.channelHandler = channelHandler;
        this.toolbarPresenter = toolbarPresenter;
        this.uiFactory = uiFactory;
        this.myConstants = myConstants;
        this.resources = resources;
        this.recordService = recordService;
        this.logger = logger;

        record = createRecordButton();
        stop = createStopButton();
        clear = createClearButton();
    }

    @Override
    public void onRecordingStateChanged(RecordingStateChangedEvent event) {
        if (event.isStarting() && !channelHandler.isChannelOpen()) {
            openChannel();
        }
    }

    @Override
    public void onDbOperation(DbOperationEvent event) {
        processDbOperationRecord(event.getDbOperationRecordDto());
        displayNewDbOperationRecords();
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, ApplicationPresenter.SLOT_MAIN, this);
    }

    @Override
    protected void onBind() {
        super.onBind();

        setRecordingState(false);
        stopRecordindWhenWindowIsClosed();

        setInSlot(SLOT_REQUESTS, filterPresenter);
        setInSlot(SLOT_STATISTICS, statisticsPresenter);
        setInSlot(SLOT_STATETEMENTS, statementPresenter);
        setInSlot(SLOT_TOOLBAR, toolbarPresenter);

        toolbarPresenter.setButtons(Lists.newArrayList(record, stop, clear));

        addRegisteredHandler(RecordingStateChangedEvent.getType(), this);
        addRegisteredHandler(DbOperationEvent.getType(), this);
    }

    private void setRecordingState(Boolean isRecording) {
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
                    onToggleRecording(false);
                }
            }
        });
    }

    private void onToggleRecording(boolean start) {
        AsyncCallback<Long> callback = getRecordingCallback(start);

        RestAction<Long> action;
        if (start) {
            action = recordService.startRecording();
        } else {
            action = recordService.stopRecording();
        }

        restDispatch.execute(action, callback);
    }

    private AsyncCallback<Long> getRecordingCallback(final boolean start) {
        return new AsyncCallbackImpl<Long>() {
            @Override
            public void handleFailure(Throwable caught) {
                setRecordingState(!start);
            }

            @Override
            public void onSuccess(Long result) {
                setRecordingState(start);
                RecordingStateChangedEvent.fire(ProfilerPresenter.this, start);
            }
        };
    }

    private ToolbarButton createStopButton() {
        return uiFactory.createToolbarButton(myConstants.stop(), resources.styles().stop(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        onToggleRecording(false);
                    }
                });
    }

    private ToolbarButton createClearButton() {
        return uiFactory.createToolbarButton(myConstants.clear(), fontsResources.icons().icon_delete(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        clearOperationRecords();
                    }
                });
    }

    private ToolbarButton createRecordButton() {
        return uiFactory.createToolbarButton(myConstants.record(), resources.styles().record(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        onToggleRecording(true);
                    }
                }, DebugIds.RECORD);
    }

    private void openChannel() {
        channelHandler.openChannel(new ChannelHandler.ChannelOpenCallback() {
            @Override
            public void onChannelOpen() {
                logger.info(DebugLogMessages.CHANNEL_OPENED);
            }
        });
    }

    private void displayNewDbOperationRecords() {
        filterPresenter.displayNewDbOperationRecords();
        statisticsPresenter.displayNewDbOperationRecords();
    }

    private void processDbOperationRecord(DbOperationRecordDto record) {
        filterPresenter.processDbOperationRecord(record);
        statisticsPresenter.processDbOperationRecord(record);
    }

    private void clearOperationRecords() {
        filterPresenter.clearOperationRecords();
        statisticsPresenter.clearOperationRecords();
        displayNewDbOperationRecords();

        ClearOperationRecordsEvent.fire(this);
    }
}
