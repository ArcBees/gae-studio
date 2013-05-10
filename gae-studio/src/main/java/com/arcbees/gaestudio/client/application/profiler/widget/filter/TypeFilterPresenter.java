/*
 * Copyright 2013 ArcBees Inc.
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
import com.arcbees.gaestudio.shared.dto.DeleteRecordDTO;
import com.arcbees.gaestudio.shared.dto.GetRecordDto;
import com.arcbees.gaestudio.shared.dto.PutRecordDto;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDto;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class TypeFilterPresenter extends PresenterWidget<TypeFilterPresenter.MyView>
        implements DbOperationRecordProcessor, TypeFilterUiHandlers {
    interface MyView extends View, HasUiHandlers<TypeFilterUiHandlers> {
        void display(List<FilterValue<OperationType>> filterValues);
    }

    private final Map<OperationType, FilterValue<OperationType>> statementsByType =
            new TreeMap<OperationType, FilterValue<OperationType>>();

    @Inject
    TypeFilterPresenter(EventBus eventBus,
                        MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDto record) {
        OperationType type = getType(record);

        FilterValue<OperationType> filterValue = statementsByType.get(type);

        if (filterValue == null) {
            filterValue = new FilterValue<OperationType>(type);
            statementsByType.put(type, filterValue);
        }

        filterValue.addRecord(record);
    }

    @Override
    public void displayNewDbOperationRecords() {
        getView().display(new ArrayList<FilterValue<OperationType>>(statementsByType.values()));
    }

    @Override
    public void clearOperationRecords() {
        statementsByType.clear();
    }

    @Override
    public void onRequestClicked(FilterValue<OperationType> filterValue) {
        FilterValueSelectedEvent.fire(this, filterValue);
    }

    private OperationType getType(DbOperationRecordDto record) {
        if (record instanceof GetRecordDto) {
            return OperationType.GET;
        } else if (record instanceof PutRecordDto) {
            return OperationType.PUT;
        } else if (record instanceof DeleteRecordDTO) {
            return OperationType.DELETE;
        } else if (record instanceof QueryRecordDto) {
            return OperationType.QUERY;
        } else {
            return OperationType.UNKNOWN;
        }
    }
}
