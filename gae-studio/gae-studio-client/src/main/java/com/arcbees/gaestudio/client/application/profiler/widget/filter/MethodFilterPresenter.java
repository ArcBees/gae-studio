/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import java.util.Map;
import java.util.TreeMap;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.client.application.profiler.event.FilterValueSelectedEvent;
import com.arcbees.gaestudio.client.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.client.dto.stacktrace.StackTraceElementDto;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class MethodFilterPresenter extends PresenterWidget<MethodFilterPresenter.MyView>
        implements DbOperationRecordProcessor, MethodFilterUiHandlers {
    interface MyView extends View, HasUiHandlers<MethodFilterUiHandlers> {
        void display(Map<String, Map<String, FilterValue<String>>> statementsByMethodAndClass);
    }

    private final Map<String, Map<String, FilterValue<String>>> statementsByMethodAndClass = new TreeMap<String,
            Map<String, FilterValue<String>>>();

    @Inject
    MethodFilterPresenter(EventBus eventBus,
                          MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDto record) {
        StackTraceElementDto stackTraceElementDTO = record.getCallerStackTraceElement();
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
