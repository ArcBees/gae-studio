/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.client.MyConstants;
import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.profiler.event.ClearOperationRecordsEvent;
import com.arcbees.gaestudio.client.application.profiler.event.RecordingStateChangedEvent;
import com.arcbees.gaestudio.client.application.profiler.widget.DetailsPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.ProfilerToolbarPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.StatementPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.StatisticsPresenter;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.FiltersPresenter;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsResult;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

import java.util.ArrayList;

public class ProfilerPresenter extends Presenter<ProfilerPresenter.MyView, ProfilerPresenter.MyProxy> implements
        RecordingStateChangedEvent.RecordingStateChangedHandler,
        ClearOperationRecordsEvent.ClearOperationRecordsHandler {

    public interface MyView extends View {
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.profiler)
    public interface MyProxy extends ProxyPlace<ProfilerPresenter> {
    }

    public static final Object TYPE_SetRequestPanelContent = new Object();
    public static final Object TYPE_SetStatisticsPanelContent = new Object();
    public static final Object TYPE_SetStatementPanelContent = new Object();
    public static final Object TYPE_SetDetailsPanelContent = new Object();
    public static final Object TYPE_SetToolbarContent = new Object();
    private static final int TICK_DELTA_MILLISEC = 1000;

    private final DispatchAsync dispatcher;
    private final FiltersPresenter filterPresenter;
    private final StatisticsPresenter statisticsPresenter;
    private final MyConstants myConstants;
    private final StatementPresenter statementPresenter;
    private final DetailsPresenter detailsPresenter;
    private final ProfilerToolbarPresenter profilerToolbarPresenter;

    private long lastDbOperationRecordId = 0L;
    private boolean isProcessing = false;

    @Inject
    public ProfilerPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                             final DispatchAsync dispatcher, final FiltersPresenter filterPresenter,
                             final StatisticsPresenter statisticsPresenter, final MyConstants myConstants,
                             final StatementPresenter statementPresenter,
                             final DetailsPresenter detailsPresenter,
                             final ProfilerToolbarPresenter profilerToolbarPresenter) {
        super(eventBus, view, proxy);

        this.dispatcher = dispatcher;
        this.filterPresenter = filterPresenter;
        this.statisticsPresenter = statisticsPresenter;
        this.myConstants = myConstants;
        this.statementPresenter = statementPresenter;
        this.detailsPresenter = detailsPresenter;
        this.profilerToolbarPresenter = profilerToolbarPresenter;
    }

    @Override
    public void onRecordingStateChanged(RecordingStateChangedEvent event) {
        if (event.isStarting()) {
            lastDbOperationRecordId = event.getCurrentRecordId();
            tick.scheduleRepeating(TICK_DELTA_MILLISEC);
        } else {
            tick.cancel();
        }
    }

    @Override
    public void onClearOperationRecords(ClearOperationRecordsEvent event) {
        clearOperationRecords();
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

    private void getNewDbOperationRecords() {
        if (!isProcessing) {
            isProcessing = true;
            dispatcher.execute(
                    new GetNewDbOperationRecordsAction.Builder(lastDbOperationRecordId).maxResults(100).build(),
                    new AsyncCallback<GetNewDbOperationRecordsResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            onGetNewDbOperationRecordsFailed();
                        }

                        @Override
                        public void onSuccess(GetNewDbOperationRecordsResult result) {
                            processNewDbOperationRecords(result.getRecords());
                            isProcessing = false;
                        }
                    });
        }
    }

    private void onGetNewDbOperationRecordsFailed() {
        Message message = new Message(myConstants.errorWhileGettingNewDbOperationRecords(), MessageStyle.ERROR);
        DisplayMessageEvent.fire(this, message);
        isProcessing = false;
    }

    // TODO properly handle any missing records
    private void processNewDbOperationRecords(ArrayList<DbOperationRecordDto> records) {
        if (!records.isEmpty()) {
            for (DbOperationRecordDto record : records) {
                processDbOperationRecord(record);
                lastDbOperationRecordId = Math.max(lastDbOperationRecordId, record.getStatementId());
            }
            displayNewDbOperationRecords();
        }
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

    private Timer tick = new Timer() {
        @Override
        public void run() {
            getNewDbOperationRecords();
        }
    };
}
