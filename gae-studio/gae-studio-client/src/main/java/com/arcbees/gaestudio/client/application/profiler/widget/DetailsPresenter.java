/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.gaestudio.client.application.profiler.event.ClearOperationRecordsEvent;
import com.arcbees.gaestudio.client.application.profiler.event.StatementSelectedEvent;
import com.arcbees.gaestudio.client.dto.DbOperationRecordDto;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class DetailsPresenter extends PresenterWidget<DetailsPresenter.MyView> implements
        StatementSelectedEvent.StatementSelectedHandler, ClearOperationRecordsEvent.ClearOperationRecordsHandler {
    interface MyView extends View {
        void displayStatementDetails(DbOperationRecordDto record);

        void clear();
    }

    @Inject
    DetailsPresenter(EventBus eventBus,
                     MyView view) {
        super(eventBus, view);
    }

    @Override
    public void onStatementSelected(StatementSelectedEvent event) {
        DbOperationRecordDto record = event.getRecord();

        getView().displayStatementDetails(record);
    }

    @Override
    public void onClearOperationRecords(ClearOperationRecordsEvent event) {
        getView().clear();
    }

    @Override
    protected void onBind() {
        super.onBind();

        addRegisteredHandler(StatementSelectedEvent.getType(), this);
        addRegisteredHandler(ClearOperationRecordsEvent.getType(), this);
    }
}
