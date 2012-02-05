/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.client.application.ApplicationPresenter;
import com.arcbees.gaestudio.client.place.NameTokens;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsResult;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
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

public class ProfilerPresenter extends Presenter<ProfilerPresenter.MyView, ProfilerPresenter.MyProxy> {

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
    
    private final DispatchAsync dispatcher;

    private final RequestPresenter requestPresenter;
    private final StatisticsPresenter statisticsPresenter;
    private final StatementPresenter statementPresenter;
    private final DetailsPresenter detailsPresenter;

    private long lastDbOperationRecordId;

    @Inject
    public ProfilerPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                             final DispatchAsync dispatcher, final RequestPresenter requestPresenter,
                             final StatisticsPresenter statisticsPresenter, final StatementPresenter statementPresenter,
                             final DetailsPresenter detailsPresenter) {
        super(eventBus, view, proxy);

        this.dispatcher = dispatcher;

        this.requestPresenter = requestPresenter;
        this.statisticsPresenter = statisticsPresenter;
        this.statementPresenter = statementPresenter;
        this.detailsPresenter = detailsPresenter;

        this.lastDbOperationRecordId = 0L;
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }
    
    @Override
    protected void onBind() {
        super.onBind();
        
        setInSlot(TYPE_SetRequestPanelContent, requestPresenter);
        setInSlot(TYPE_SetStatisticsPanelContent, statisticsPresenter);
        setInSlot(TYPE_SetStatementPanelContent, statementPresenter);
        setInSlot(TYPE_SetDetailsPanelContent, detailsPresenter);

        getNewDbOperationRecords();
        new Timer() {
            @Override
            public void run() {
                getNewDbOperationRecords();
            }
        }.scheduleRepeating(1000);
    }

    private void getNewDbOperationRecords() {
        dispatcher.execute(
                new GetNewDbOperationRecordsAction.Builder(lastDbOperationRecordId).maxResults(100).build(),
                new AsyncCallback<GetNewDbOperationRecordsResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        // TODO implement
                    }

                    @Override
                    public void onSuccess(GetNewDbOperationRecordsResult result) {
                        processNewDbOperationRecords(result.getNewLastId(), result.getRecords());
                    }
                });
    }

    // TODO properly handle any missing records
    // TODO properly handle situations (such as resyncs) where the records come out of order
    private void processNewDbOperationRecords(long newLastId, ArrayList<DbOperationRecord> records) {
        lastDbOperationRecordId = newLastId;
        for (DbOperationRecord record : records) {
            requestPresenter.processDbOperationRecord(record);
            statisticsPresenter.processDbOperationRecord(record);
            statementPresenter.processDbOperationRecord(record);
            detailsPresenter.processDbOperationRecord(record);
        }
        requestPresenter.displayNewDbOperationRecords();
        statisticsPresenter.displayNewDbOperationRecords();
        statementPresenter.displayNewDbOperationRecords();
        detailsPresenter.displayNewDbOperationRecords();
    }

}
