/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
