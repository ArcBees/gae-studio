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

import java.util.Map;
import java.util.TreeMap;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.client.application.profiler.event.FilterValueSelectedEvent;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.stacktrace.StackTraceElementDto;
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
