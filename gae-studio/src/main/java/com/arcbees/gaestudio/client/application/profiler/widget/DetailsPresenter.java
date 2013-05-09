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
import com.arcbees.gaestudio.client.application.profiler.event.StatementSelectedEvent;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
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
