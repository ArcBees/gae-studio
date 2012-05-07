/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.request;

import com.arcbees.gaestudio.client.application.event.RequestSelectedEvent;
import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RequestPresenter extends PresenterWidget<RequestPresenter.MyView>
        implements DbOperationRecordProcessor, RequestUiHandlers {

    public interface MyView extends View, HasUiHandlers<RequestUiHandlers> {
        void updateRequests(Iterable<RequestStatistics> requestStatistics);

        void updateRequest(RequestStatistics requestStatistics);
    }

    private final Map<Long, RequestStatistics> statisticsByRequestId =
            new TreeMap<Long, RequestStatistics>();
    private final List<RequestStatistics> requestStatisticsToDisplay = new ArrayList<RequestStatistics>();

    @Inject
    public RequestPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
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
        requestStatisticsToDisplay.add(statistics);
    }

    @Override
    public void displayNewDbOperationRecords() {
        getView().updateRequests(requestStatisticsToDisplay);
        requestStatisticsToDisplay.clear();
    }

    @Override
    public void onRequestClicked(Long requestId) {
        getEventBus().fireEvent(new RequestSelectedEvent(requestId));
    }

}
