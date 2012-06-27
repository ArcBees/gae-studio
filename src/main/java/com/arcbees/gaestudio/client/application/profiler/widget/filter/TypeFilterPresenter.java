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
        void display(List<FilterValue<String>> filterValues);
    }

    private static final String GET = "Get";
    private static final String PUT = "Put";
    private static final String DELETE = "Delete";
    private static final String UNKNOWN = "Unknown";
    private static final String QUERY = "Query";

    private final Map<String, FilterValue<String>> statementsByType = new TreeMap<String, FilterValue<String>>();

    @Inject
    public TypeFilterPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDTO record) {
        String type = getType(record);

        FilterValue<String> filterValue = statementsByType.get(type);

        if (filterValue == null) {
            filterValue = new FilterValue<String>(type);
            statementsByType.put(type, filterValue);
        }

        filterValue.addRecord(record);
    }

    @Override
    public void displayNewDbOperationRecords() {
        getView().display(new ArrayList<FilterValue<String>>(statementsByType.values()));
    }

    @Override
    public void clearOperationRecords() {
        statementsByType.clear();
    }

    @Override
    public void onRequestClicked(FilterValue<String> filterValue) {
        FilterValueSelectedEvent.fire(this, filterValue);
    }

    private String getType(DbOperationRecordDTO record) {
        if (record instanceof GetRecordDTO) {
            return GET;
        } else if (record instanceof PutRecordDTO) {
            return PUT;
        } else if (record instanceof DeleteRecordDTO) {
            return DELETE;
        }  else if (record instanceof QueryRecordDTO) {
            return QUERY;
        } else {
            return UNKNOWN;
        }
    }

}
