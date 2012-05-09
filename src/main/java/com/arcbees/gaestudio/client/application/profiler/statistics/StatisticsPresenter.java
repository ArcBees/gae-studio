/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.statistics;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDTO;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.HashSet;

public class StatisticsPresenter extends PresenterWidget<StatisticsPresenter.MyView>
        implements DbOperationRecordProcessor {

    public interface MyView extends View {
        void updateRequestCount(Integer requestCount);
        void updateStatementCount(Integer statementCount);
        void updateTotalExecutionTimeMs(Integer totalExecutionTimeMs);
        void updateTotalObjectsRetrieved(Integer totalObjectsRetrieved);
        void updateTotalDataReceived(Integer totalDataReceived);
    }

    private final HashSet<Long> knownRequestIds;
    private Integer statementCount;
    private Integer totalExecutionTimeMs;
    private Integer totalObjectsRetrieved;
    private Integer totalDataReceived;

    @Inject
    public StatisticsPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);

        this.knownRequestIds = new HashSet<Long>();
        this.statementCount = 0;
        this.totalExecutionTimeMs = 0;
        this.totalObjectsRetrieved = 0;
        this.totalDataReceived = 0;
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDTO record) {
        final long requestId = record.getRequestId();
        
        knownRequestIds.add(requestId);
        statementCount++;
        totalExecutionTimeMs += record.getExecutionTimeMs();
        
        if (record instanceof QueryRecordDTO) {
            QueryRecordDTO queryRecord = (QueryRecordDTO)record;
            totalObjectsRetrieved += queryRecord.getQueryResult().getResultSize();
            totalDataReceived += queryRecord.getQueryResult().getSerializedSize();
        }
    }

    @Override
    public void displayNewDbOperationRecords() {
        getView().updateRequestCount(knownRequestIds.size());
        getView().updateStatementCount(statementCount);
        getView().updateTotalExecutionTimeMs(totalExecutionTimeMs);
        getView().updateTotalObjectsRetrieved(totalObjectsRetrieved);
        getView().updateTotalDataReceived(totalDataReceived);
    }
}
