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

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.profiler.event.ClearOperationRecordsEvent;
import com.arcbees.gaestudio.client.application.profiler.event.RecordingStateChangedEvent;
import com.arcbees.gaestudio.client.application.profiler.widget.DetailsPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.ProfilerToolbarPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.StatementPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.StatisticsPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.FiltersPresenter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.client.rest.OperationsService;
import com.arcbees.gaestudio.client.util.DebugLogMessages;
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

    public static final Object TYPE_SetRequestPanelContent = new Object();
    public static final Object TYPE_SetStatisticsPanelContent = new Object();
    public static final Object TYPE_SetStatementPanelContent = new Object();
    public static final Object TYPE_SetDetailsPanelContent = new Object();
    public static final Object TYPE_SetToolbarContent = new Object();

    private final OperationsService operationsService;
    private final FiltersPresenter filterPresenter;
    private final StatisticsPresenter statisticsPresenter;
    private final StatementPresenter statementPresenter;
    private final DetailsPresenter detailsPresenter;
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
                      OperationsService operationsService,
                      FiltersPresenter filterPresenter,
                      StatisticsPresenter statisticsPresenter,
                      StatementPresenter statementPresenter,
                      DetailsPresenter detailsPresenter,
                      ProfilerToolbarPresenter profilerToolbarPresenter,
                      ChannelFactory channelFactory,
                      DbOperationDeserializer dbOperationDeserializer,
                      Logger logger,
                      AppConfig appConfig) {
        super(eventBus, view, proxy);

        this.operationsService = operationsService;
        this.filterPresenter = filterPresenter;
        this.statisticsPresenter = statisticsPresenter;
        this.statementPresenter = statementPresenter;
        this.detailsPresenter = detailsPresenter;
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
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }

    @Override
    protected void onBind() {
        super.onBind();

        setInSlot(TYPE_SetRequestPanelContent, filterPresenter);
        setInSlot(TYPE_SetStatisticsPanelContent, statisticsPresenter);
        setInSlot(TYPE_SetStatementPanelContent, statementPresenter);
        setInSlot(TYPE_SetDetailsPanelContent, detailsPresenter);
        setInSlot(TYPE_SetToolbarContent, profilerToolbarPresenter);

        addRegisteredHandler(RecordingStateChangedEvent.getType(), this);
        addRegisteredHandler(ClearOperationRecordsEvent.getType(), this);
    }

    private void closeChannel() {
        if (socket != null) {
            socket.close();
        }
    }

    private void openChannel() {
        operationsService.getToken(clientId, new MethodCallback<Token>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
            }

            @Override
            public void onSuccess(Method method, Token token) {
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
