/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.client.application.profiler.event.FilterValueSelectedEvent;
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
        void display(List<FilterValue<Long>> filterValues);
    }

    private final Map<Long, FilterValue<Long>> statementsByRequest = new TreeMap<Long, FilterValue<Long>>();

    @Inject
    public RequestFilterPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDTO record) {
        Long requestId = record.getRequestId();

        FilterValue<Long> filterValue = statementsByRequest.get(requestId);

        if (filterValue == null) {
            filterValue = new FilterValue<Long>(requestId);
            statementsByRequest.put(requestId, filterValue);
        }

        filterValue.addRecord(record);
    }

    @Override
    public void displayNewDbOperationRecords() {
        getView().display(new ArrayList<FilterValue<Long>>(statementsByRequest.values()));
    }

    @Override
    public void onRequestClicked(FilterValue<Long> filterValue) {
        FilterValueSelectedEvent.fire(this, filterValue);
    }

}
