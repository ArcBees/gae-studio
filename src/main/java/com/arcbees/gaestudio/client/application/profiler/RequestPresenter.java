/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.client.application.event.RequestSelectedEvent;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.TreeMap;

public class RequestPresenter extends PresenterWidget<RequestPresenter.MyView>
        implements DbOperationRecordProcessor, RequestUiHandlers {

    public interface MyView extends View, HasUiHandlers<RequestUiHandlers> {
        void updateRequests(Iterable<RequestStatistics> requestStatistics);
    }

    private final DispatchAsync dispatcher;
    
    private final TreeMap<Long, RequestStatistics> statisticsByRequestId =
            new TreeMap<Long, RequestStatistics>();

    @Inject
    public RequestPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher) {
        super(eventBus, view);
        this.dispatcher = dispatcher;
    }
    
    @Override
    public void processDbOperationRecord(DbOperationRecord record) {
        final long requestId = record.getRequestId();
        RequestStatistics statistics;
        if (!statisticsByRequestId.containsKey(requestId)) {
            statistics = new RequestStatistics(requestId, 1, record.getExecutionTimeMs());
            statisticsByRequestId.put(requestId, statistics);
        } else {
            statistics = statisticsByRequestId.get(requestId);
            statistics.incrementStatementCount();
            statistics.incrementExecutionTimeMs(record.getExecutionTimeMs());
        }
    }

    @Override
    public void displayNewDbOperationRecords() {
        getView().updateRequests(statisticsByRequestId.values());
    }

    @Override
    public void onRequestClicked(Long requestId) {
        getEventBus().fireEvent(new RequestSelectedEvent(requestId));
    }

    class RequestStatistics {
        private long requestId;
        private int statementCount;
        private int executionTimeMs;

        RequestStatistics(long requestId, int statementCount, int executionTimeMs) {
            this.requestId = requestId;
            this.statementCount = statementCount;
            this.executionTimeMs = executionTimeMs;
        }

        long getRequestId() {
            return requestId;
        }

        int getStatementCount() {
            return statementCount;
        }

        void incrementStatementCount() {
            statementCount++;
        }

        int getExecutionTimeMs() {
            return executionTimeMs;
        }

        void incrementExecutionTimeMs(int deltaTimeMs) {
            executionTimeMs += deltaTimeMs;
        }
    }

}
