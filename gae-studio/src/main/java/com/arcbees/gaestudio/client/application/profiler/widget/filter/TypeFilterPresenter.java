/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
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
    public interface MyView extends View, HasUiHandlers<TypeFilterUiHandlers> {
        void display(List<FilterValue<OperationType>> filterValues);
    }

    private final Map<OperationType, FilterValue<OperationType>> statementsByType =
            new TreeMap<OperationType, FilterValue<OperationType>>();

    @Inject
    public TypeFilterPresenter(final EventBus eventBus, final MyView view) {
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
