/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.gaestudio.client.application.profiler.event.FilterValueSelectedEvent;
import com.arcbees.gaestudio.client.application.profiler.event.StatementSelectedEvent;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterValue;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class StatementPresenter extends PresenterWidget<StatementPresenter.MyView>
        implements FilterValueSelectedEvent.FilterValueSelectedHandler, StatementUiHandlers {

    public interface MyView extends View, HasUiHandlers<StatementUiHandlers> {
        void displayStatements(FilterValue filterValue);
    }

    @Inject
    public StatementPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(FilterValueSelectedEvent.getType(), this);
    }

    @Override
    public void onFilterValueSelected(FilterValueSelectedEvent event) {
        FilterValue filterValue = event.getFilterValue();
        getView().displayStatements(filterValue);
    }

    @Override
    public void onStatementClicked(DbOperationRecordDTO record) {
        StatementSelectedEvent.fire(this, record);
    }

}
