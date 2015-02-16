/**
 * Copyright 2015 ArcBees Inc.
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

import java.util.ArrayList;
import java.util.List;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;

public class FilterValue<T> {
    private final List<DbOperationRecordDto> statements = new ArrayList<DbOperationRecordDto>();

    private T key;
    private int executionTimeMs;

    public FilterValue(T key) {
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    public int getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void addRecord(DbOperationRecordDto record) {
        statements.add(record);
        executionTimeMs += record.getExecutionTimeMs();
    }

    public List<DbOperationRecordDto> getStatements() {
        return statements;
    }

    public int getStatementCount() {
        return statements.size();
    }
}
