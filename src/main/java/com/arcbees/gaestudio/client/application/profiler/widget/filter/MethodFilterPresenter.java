/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.client.application.profiler.event.FilterValueSelectedEvent;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.arcbees.gaestudio.shared.stacktrace.StackTraceElementDTO;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.Map;
import java.util.TreeMap;

public class MethodFilterPresenter extends PresenterWidget<MethodFilterPresenter.MyView>
        implements DbOperationRecordProcessor, MethodFilterUiHandlers {

    public interface MyView extends View, HasUiHandlers<MethodFilterUiHandlers> {
        void display(Map<String, Map<String, FilterValue<String>>> statementsByMethodAndClass);
    }

    private final Map<String, Map<String, FilterValue<String>>> statementsByMethodAndClass = new TreeMap<String,
            Map<String, FilterValue<String>>>();

    @Inject
    public MethodFilterPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
        
        getView().setUiHandlers(this);
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDTO record) {
        StackTraceElementDTO stackTraceElementDTO = record.getCallerStackTraceElement();
        String className = stackTraceElementDTO.getClassName();

        Map<String, FilterValue<String>> statementsByMethod = statementsByMethodAndClass.get(className);

        if (statementsByMethod == null) {
            statementsByMethod = new TreeMap<String, FilterValue<String>>();
            statementsByMethodAndClass.put(className, statementsByMethod);
        }

        String methodName = stackTraceElementDTO.getMethodName();
        FilterValue<String> filterValue = statementsByMethod.get(methodName);

        if (filterValue == null) {
            filterValue = new FilterValue<String>(methodName);
            statementsByMethod.put(methodName, filterValue);
        }

        filterValue.addRecord(record);
    }

    @Override
    public void displayNewDbOperationRecords() {
        getView().display(statementsByMethodAndClass);
    }

    @Override
    public void clearOperationRecords() {
        statementsByMethodAndClass.clear();
    }

    @Override
    public void onMethodClicked(String className, String methodName) {
        if (statementsByMethodAndClass.containsKey(className)) {
            FilterValue<String> filterValue = statementsByMethodAndClass.get(className).get(methodName);

            if (filterValue != null) {
                FilterValueSelectedEvent.fire(this, filterValue);
            }
        }
    }

}
