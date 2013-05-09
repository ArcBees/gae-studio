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

package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.gaestudio.client.application.profiler.event.ClearOperationRecordsEvent;
import com.arcbees.gaestudio.client.application.profiler.event.FilterValueSelectedEvent;
import com.arcbees.gaestudio.client.application.profiler.event.StatementSelectedEvent;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterValue;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class StatementPresenter extends PresenterWidget<StatementPresenter.MyView> implements
        FilterValueSelectedEvent.FilterValueSelectedHandler, StatementUiHandlers,
        ClearOperationRecordsEvent.ClearOperationRecordsHandler {
    interface MyView extends View, HasUiHandlers<StatementUiHandlers> {
        void displayStatements(FilterValue<?> filterValue);

        void clear();
    }

    @Inject
    StatementPresenter(EventBus eventBus,
                       MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    public void onFilterValueSelected(FilterValueSelectedEvent event) {
        FilterValue<?> filterValue = event.getFilterValue();
        getView().displayStatements(filterValue);
    }

    @Override
    public void onStatementClicked(DbOperationRecordDto record) {
        StatementSelectedEvent.fire(this, record);
    }

    @Override
    public void onClearOperationRecords(ClearOperationRecordsEvent event) {
        getView().clear();
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(FilterValueSelectedEvent.getType(), this);
        addRegisteredHandler(ClearOperationRecordsEvent.getType(), this);
    }
}
