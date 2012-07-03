/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.client.application.profiler.event.FilterValueSelectedEvent;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.arcbees.gaestudio.shared.dto.DeleteRecordDTO;
import com.arcbees.gaestudio.shared.dto.GetRecordDTO;
import com.arcbees.gaestudio.shared.dto.PutRecordDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDTO;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDTO record) {
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

    private OperationType getType(DbOperationRecordDTO record) {
        if (record instanceof GetRecordDTO) {
            return OperationType.GET;
        } else if (record instanceof PutRecordDTO) {
            return OperationType.PUT;
        } else if (record instanceof DeleteRecordDTO) {
            return OperationType.DELETE;
        } else if (record instanceof QueryRecordDTO) {
            return OperationType.QUERY;
        } else {
            return OperationType.UNKNOWN;
        }
    }

}
