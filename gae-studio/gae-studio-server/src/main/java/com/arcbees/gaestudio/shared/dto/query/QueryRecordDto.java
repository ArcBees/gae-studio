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

package com.arcbees.gaestudio.shared.dto.query;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.dto.OperationKind;
import com.arcbees.gaestudio.shared.dto.stacktrace.StackTraceElementDto;

public class QueryRecordDto extends DbOperationRecordDto {
    private QueryDto query;
    private QueryResultDto queryResult;

    @SuppressWarnings("unused")
    public QueryRecordDto() {
    }

    public QueryRecordDto(
            QueryDto query,
            QueryResultDto queryResult,
            StackTraceElementDto callerStackTraceElement,
            Long requestId,
            Long statementId,
            Integer executionTimeMs) {
        super(callerStackTraceElement, requestId, statementId, executionTimeMs, OperationKind.QUERY);

        this.query = query;
        this.queryResult = queryResult;
    }

    public QueryDto getQuery() {
        return query;
    }

    public QueryResultDto getQueryResult() {
        return queryResult;
    }

    public void setQuery(QueryDto query) {
        this.query = query;
    }

    public void setQueryResult(QueryResultDto queryResult) {
        this.queryResult = queryResult;
    }
}
