/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.statement;

import com.arcbees.gaestudio.client.application.event.RequestSelectedEvent;
import com.arcbees.gaestudio.client.application.event.StatementSelectedEvent;
import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import java.util.ArrayList;
import java.util.HashMap;

public class StatementPresenter extends PresenterWidget<StatementPresenter.MyView>
        implements DbOperationRecordProcessor, RequestSelectedEvent.RequestSelectedHandler, StatementUiHandlers {

    public interface MyView extends View, HasUiHandlers<StatementUiHandlers> {
        Long getCurrentlyDisplayedRequestId();
        void displayStatementsForRequest(Long requestId, ArrayList<DbOperationRecordDTO> statements);
    }

    private final HashMap<Long, ArrayList<DbOperationRecordDTO>> statementsByRequestId =
            new HashMap<Long, ArrayList<DbOperationRecordDTO>>();

    @Inject
    public StatementPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

    @Override
    protected void onBind() {
        super.onBind();
        addRegisteredHandler(RequestSelectedEvent.getType(), this);
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDTO record) {
        final Long requestId = record.getRequestId();
        if (statementsByRequestId.containsKey(requestId)) {
            statementsByRequestId.get(requestId).add(record);
        } else {
            ArrayList<DbOperationRecordDTO> list = new ArrayList<DbOperationRecordDTO>();
            list.add(record);
            statementsByRequestId.put(requestId, list);
        }
    }

    @Override
    public void displayNewDbOperationRecords() {
        Long requestId = getView().getCurrentlyDisplayedRequestId();
        getView().displayStatementsForRequest(requestId, statementsByRequestId.get(requestId));
    }

    @Override
    public void onRequestSelected(RequestSelectedEvent event) {
        Long requestId = event.getRequestId();
        getView().displayStatementsForRequest(requestId, statementsByRequestId.get(requestId));
    }

    @Override
    public void onStatementClicked(Long statementId) {
        getEventBus().fireEvent(new StatementSelectedEvent(statementId));
    }

}
