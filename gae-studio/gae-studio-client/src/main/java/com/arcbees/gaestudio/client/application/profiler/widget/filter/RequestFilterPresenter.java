/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.client.application.profiler.event.FilterValueSelectedEvent;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class RequestFilterPresenter extends PresenterWidget<RequestFilterPresenter.MyView>
        implements DbOperationRecordProcessor, RequestFilterUiHandlers {
    interface MyView extends View, HasUiHandlers<RequestFilterUiHandlers> {
        void display(List<FilterValue<Long>> filterValues);
    }

    private final Map<Long, FilterValue<Long>> statementsByRequest = new TreeMap<Long, FilterValue<Long>>();

    @Inject
    RequestFilterPresenter(EventBus eventBus,
                           MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDto record) {
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
    public void clearOperationRecords() {
        statementsByRequest.clear();
    }

    @Override
    public void onRequestClicked(FilterValue<Long> filterValue) {
        FilterValueSelectedEvent.fire(this, filterValue);
    }
}
