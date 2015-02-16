/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
    RequestFilterPresenter(
            EventBus eventBus,
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
