/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.client.application.profiler.event.RequestSelectedEvent;
import com.arcbees.gaestudio.client.application.profiler.widget.RequestStatistics;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FilterPresenter extends PresenterWidget<FilterPresenter.MyView>
        implements DbOperationRecordProcessor, FilterUiHandlers {

    public interface MyView extends View, HasUiHandlers<FilterUiHandlers> {
        void displayRequests(List<RequestStatistics> requestStatistics);
    }

    private final Map<Long, RequestStatistics> requestsByRequestId =
            new TreeMap<Long, RequestStatistics>();
    private final List<RequestStatistics> requests = new ArrayList<RequestStatistics>();

    @Inject
    public FilterPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }
    
    @Override
    public void processDbOperationRecord(DbOperationRecordDTO record) {
        final long requestId = record.getRequestId();
        RequestStatistics statistics;
        if (!requestsByRequestId.containsKey(requestId)) {
            statistics = new RequestStatistics(requestId, 1, record.getExecutionTimeMs());
            requestsByRequestId.put(requestId, statistics);
            requests.add(statistics);
        } else {
            statistics = requestsByRequestId.get(requestId);
            statistics.incrementStatementCount();
            statistics.incrementExecutionTimeMs(record.getExecutionTimeMs());
        }
    }

    @Override
    public void displayNewDbOperationRecords() {
        getView().displayRequests(requests);
    }

    @Override
    public void onRequestClicked(Long requestId) {
        getEventBus().fireEvent(new RequestSelectedEvent(requestId));
    }

}
