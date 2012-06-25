/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.client.application.profiler.event.RequestSelectedEvent;
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

public class RequestFilterPresenter extends PresenterWidget<RequestFilterPresenter.MyView>
        implements DbOperationRecordProcessor, RequestFilterUiHandlers {

    public interface MyView extends View, HasUiHandlers<RequestFilterUiHandlers> {
        void displayRequests(List<RequestFilterValue> requestStatistics);
    }

    private final Map<Long, RequestFilterValue> requestsByRequestId = new TreeMap<Long, RequestFilterValue>();

    @Inject
    public RequestFilterPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDTO record) {
        Long requestId = record.getRequestId();

        RequestFilterValue filterValue = requestsByRequestId.get(requestId);

        if (filterValue == null) {
            filterValue = new RequestFilterValue(requestId);
            requestsByRequestId.put(requestId, filterValue);
        }

        filterValue.addRecord(record);
    }

    @Override
    public void displayNewDbOperationRecords() {
        getView().displayRequests(new ArrayList<RequestFilterValue>(requestsByRequestId.values()));
    }

    @Override
    public void onRequestClicked(Long requestId) {
        getEventBus().fireEvent(new RequestSelectedEvent(requestId));
    }

}
