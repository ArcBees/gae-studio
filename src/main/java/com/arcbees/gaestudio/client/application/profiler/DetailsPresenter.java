/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.client.application.event.StatementSelectedEvent;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.HashMap;

public class DetailsPresenter extends PresenterWidget<DetailsPresenter.MyView>
        implements DbOperationRecordProcessor, StatementSelectedEvent.StatementSelectedHandler {

    public interface MyView extends View {
        void displayStatementDetails(DbOperationRecord record);
    }
    
    private final HashMap<Long, DbOperationRecord> statementsById = new HashMap<Long, DbOperationRecord>();

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
    public void processDbOperationRecord(DbOperationRecord record) {
        statementsById.put(record.getStatementId(), record);
    }

    @Override
    public void displayNewDbOperationRecords() {
        // Nothing to do here
    }

    @Override
    public void onStatementSelected(StatementSelectedEvent event) {
        getView().displayStatementDetails(statementsById.get(event.getStatementId()));
    }

}
