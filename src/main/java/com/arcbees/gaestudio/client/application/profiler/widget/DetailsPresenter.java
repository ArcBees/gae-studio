/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.gaestudio.client.application.profiler.event.StatementSelectedEvent;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class DetailsPresenter extends PresenterWidget<DetailsPresenter.MyView>
        implements StatementSelectedEvent.StatementSelectedHandler {

    public interface MyView extends View {
        void displayStatementDetails(DbOperationRecordDTO record);
    }

    @Inject
    public DetailsPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(StatementSelectedEvent.getType(), this);
    }

    @Override
    public void onStatementSelected(StatementSelectedEvent event) {
        DbOperationRecordDTO record = event.getRecord();

        getView().displayStatementDetails(record);
    }

}
