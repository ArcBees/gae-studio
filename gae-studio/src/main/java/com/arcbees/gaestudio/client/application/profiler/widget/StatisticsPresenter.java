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

import java.util.HashSet;

import com.arcbees.gaestudio.client.application.profiler.DbOperationRecordProcessor;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDto;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class StatisticsPresenter extends PresenterWidget<StatisticsPresenter.MyView>
        implements DbOperationRecordProcessor {
    interface MyView extends View {
        void updateRequestCount(Integer requestCount);

        void updateStatementCount(Integer statementCount);

        void updateTotalExecutionTimeMs(Integer totalExecutionTimeMs);

        void updateTotalObjectsRetrieved(Integer totalObjectsRetrieved);

        void updateTotalDataReceived(Integer totalDataReceived);
    }

    private final HashSet<Long> knownRequestIds = new HashSet<Long>();
    private Integer statementCount;
    private Integer totalExecutionTimeMs;
    private Integer totalObjectsRetrieved;
    private Integer totalDataReceived;

    @Inject
    StatisticsPresenter(EventBus eventBus,
                        MyView view) {
        super(eventBus, view);

        initStats();
    }

    @Override
    public void processDbOperationRecord(DbOperationRecordDto record) {
        final long requestId = record.getRequestId();

        knownRequestIds.add(requestId);
        statementCount++;
        totalExecutionTimeMs += record.getExecutionTimeMs();

        if (record instanceof QueryRecordDto) {
            QueryRecordDto queryRecord = (QueryRecordDto) record;
            totalObjectsRetrieved += queryRecord.getQueryResult().getResultSize();
            totalDataReceived += queryRecord.getQueryResult().getSerializedSize();
        }
    }

    @Override
    public void displayNewDbOperationRecords() {
        getView().updateRequestCount(knownRequestIds.size());
        getView().updateStatementCount(statementCount);
        getView().updateTotalExecutionTimeMs(totalExecutionTimeMs);
        getView().updateTotalObjectsRetrieved(totalObjectsRetrieved);
        getView().updateTotalDataReceived(totalDataReceived);
    }

    @Override
    public void clearOperationRecords() {
        initStats();
    }

    private void initStats() {
        knownRequestIds.clear();
        this.statementCount = 0;
        this.totalExecutionTimeMs = 0;
        this.totalObjectsRetrieved = 0;
        this.totalDataReceived = 0;
    }
}
