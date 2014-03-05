/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler;

import java.util.logging.Logger;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
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
import com.arcbees.gaestudio.client.rest.OperationsService;
import com.arcbees.gaestudio.client.rest.RecordService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.channel.Token;
import com.arcbees.gaestudio.shared.config.AppConfig;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.common.collect.Lists;
import com.google.gwt.appengine.channel.client.Channel;
import com.google.gwt.appengine.channel.client.ChannelError;
import com.google.gwt.appengine.channel.client.ChannelFactory;
import com.google.gwt.appengine.channel.client.Socket;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class ProfilerPresenter extends Presenter<ProfilerPresenter.MyView, ProfilerPresenter.MyProxy>
        implements RecordingStateChangedEvent.RecordingStateChangedHandler, SocketListener {
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
    private final OperationsService operationsService;
    private final FiltersPresenter filterPresenter;
    private final StatisticsPresenter statisticsPresenter;
    private final StatementPresenter statementPresenter;
    private final ChannelFactory channelFactory;
    private final String clientId;
    private final DbOperationDeserializer dbOperationDeserializer;
    private final Logger logger;
    private final UiFactory uiFactory;
    private final AppConstants myConstants;
    private final AppResources resources;
    private final RecordService recordService;
    private final ToolbarPresenter toolbarPresenter;
    private final ToolbarButton record;
    private final ToolbarButton stop;
    private final ToolbarButton clear;

    private Socket socket;
    private Boolean isRecording = false;

    @Inject
    ProfilerPresenter(EventBus eventBus,
                      MyView view,
                      MyProxy proxy,
                      RestDispatch restDispatch,
                      OperationsService operationsService,
                      FiltersPresenter filterPresenter,
                      StatisticsPresenter statisticsPresenter,
                      StatementPresenter statementPresenter,
                      ChannelFactory channelFactory,
                      DbOperationDeserializer dbOperationDeserializer,
                      Logger logger,
                      AppConfig appConfig,
                      UiFactory uiFactory,
                      AppConstants myConstants,
                      AppResources resources,
                      RecordService recordService,
                      ToolbarPresenter toolbarPresenter) {
        super(eventBus, view, proxy);

        this.restDispatch = restDispatch;
        this.operationsService = operationsService;
        this.filterPresenter = filterPresenter;
        this.statisticsPresenter = statisticsPresenter;
        this.statementPresenter = statementPresenter;
        this.channelFactory = channelFactory;
        this.toolbarPresenter = toolbarPresenter;
        this.uiFactory = uiFactory;
        this.myConstants = myConstants;
        this.resources = resources;
        this.recordService = recordService;
        this.clientId = appConfig.getClientId();
        this.dbOperationDeserializer = dbOperationDeserializer;
        this.logger = logger;

        record = createRecordButton();
        stop = createStopButton();
        clear = createClearButton();
    }

    @Override
    public void onRecordingStateChanged(RecordingStateChangedEvent event) {
        if (event.isStarting()) {
            openChannel();
        } else {
            closeChannel();
        }
    }

    @Override
    public void onOpen() {
        logger.info(DebugLogMessages.CHANNEL_OPENED);
    }

    @Override
    public void onMessage(String dbOperation) {
        DbOperationRecordDto recordDto = dbOperationDeserializer.deserialize(dbOperation);

        processDbOperationRecord(recordDto);
        displayNewDbOperationRecords();
    }

    @Override
    public void onError(ChannelError channelError) {
    }

    @Override
    public void onClose() {
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
        return uiFactory.createToolbarButton(myConstants.clear(), resources.styles().delete(),
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

    private void closeChannel() {
        if (socket != null) {
            socket.close();
        }
    }

    private void openChannel() {
        restDispatch.execute(operationsService.getToken(clientId), new AsyncCallbackImpl<Token>() {
            @Override
            public void onSuccess(Token token) {
                openChannel(token);
            }
        });
    }

    private void openChannel(Token token) {
        Channel channel = channelFactory.createChannel(token.getValue());

        socket = channel.open(this);
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
