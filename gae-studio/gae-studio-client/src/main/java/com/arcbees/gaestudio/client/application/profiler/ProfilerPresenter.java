/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
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
import com.arcbees.gaestudio.client.application.profiler.widget.ProfilerToolbarPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.StatementPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.StatisticsPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.FiltersPresenter;
import com.arcbees.gaestudio.client.debug.DebugLogMessages;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.rest.OperationsService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.channel.Token;
import com.arcbees.gaestudio.shared.config.AppConfig;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.gwt.appengine.channel.client.Channel;
import com.google.gwt.appengine.channel.client.ChannelError;
import com.google.gwt.appengine.channel.client.ChannelFactory;
import com.google.gwt.appengine.channel.client.Socket;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class ProfilerPresenter extends Presenter<ProfilerPresenter.MyView, ProfilerPresenter.MyProxy> implements
        RecordingStateChangedEvent.RecordingStateChangedHandler,
        ClearOperationRecordsEvent.ClearOperationRecordsHandler, SocketListener {
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
    private final ProfilerToolbarPresenter profilerToolbarPresenter;
    private final ChannelFactory channelFactory;
    private final String clientId;
    private final DbOperationDeserializer dbOperationDeserializer;
    private final Logger logger;

    private Socket socket;

    @Inject
    ProfilerPresenter(EventBus eventBus,
                      MyView view,
                      MyProxy proxy,
                      RestDispatch restDispatch,
                      OperationsService operationsService,
                      FiltersPresenter filterPresenter,
                      StatisticsPresenter statisticsPresenter,
                      StatementPresenter statementPresenter,
                      ProfilerToolbarPresenter profilerToolbarPresenter,
                      ChannelFactory channelFactory,
                      DbOperationDeserializer dbOperationDeserializer,
                      Logger logger,
                      AppConfig appConfig) {
        super(eventBus, view, proxy);

        this.restDispatch = restDispatch;
        this.operationsService = operationsService;
        this.filterPresenter = filterPresenter;
        this.statisticsPresenter = statisticsPresenter;
        this.statementPresenter = statementPresenter;
        this.profilerToolbarPresenter = profilerToolbarPresenter;
        this.channelFactory = channelFactory;
        this.clientId = appConfig.getClientId();
        this.dbOperationDeserializer = dbOperationDeserializer;
        this.logger = logger;
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
    public void onClearOperationRecords(ClearOperationRecordsEvent event) {
        clearOperationRecords();
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

        setInSlot(SLOT_REQUESTS, filterPresenter);
        setInSlot(SLOT_STATISTICS, statisticsPresenter);
        setInSlot(SLOT_STATETEMENTS, statementPresenter);
        setInSlot(SLOT_TOOLBAR, profilerToolbarPresenter);

        addRegisteredHandler(RecordingStateChangedEvent.getType(), this);
        addRegisteredHandler(ClearOperationRecordsEvent.getType(), this);
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
    }
}
