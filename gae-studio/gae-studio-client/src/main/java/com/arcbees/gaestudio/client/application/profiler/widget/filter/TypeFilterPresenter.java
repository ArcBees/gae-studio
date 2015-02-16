/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
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
import com.arcbees.gaestudio.shared.dto.DeleteRecordDto;
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
    TypeFilterPresenter(
            EventBus eventBus,
            MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDto record) {
        OperationType type = getType(record);

        FilterValue<OperationType> filterValue = statementsByType.get(type);

        if (filterValue == null) {
            filterValue = new FilterValue<>(type);
            statementsByType.put(type, filterValue);
        }

        filterValue.addRecord(record);
    }

    @Override
    public void displayNewDbOperationRecords() {
        getView().display(new ArrayList<>(statementsByType.values()));
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
        } else if (record instanceof DeleteRecordDto) {
            return OperationType.DELETE;
        } else if (record instanceof QueryRecordDto) {
            return OperationType.QUERY;
        } else {
            return OperationType.UNKNOWN;
        }
    }
}
