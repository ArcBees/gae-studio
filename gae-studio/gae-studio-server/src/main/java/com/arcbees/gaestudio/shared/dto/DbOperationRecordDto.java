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

package com.arcbees.gaestudio.shared.dto;

import java.io.Serializable;

import com.arcbees.gaestudio.shared.dto.stacktrace.StackTraceElementDto;

public class DbOperationRecordDto implements Serializable {
    private StackTraceElementDto callerStackTraceElement;
    private Long requestId;
    private Long statementId;
    private Integer executionTimeMs;
    private OperationKind type;

    @SuppressWarnings("unused")
    public DbOperationRecordDto() {
        this.requestId = -1L;
        this.statementId = -1L;
        this.executionTimeMs = -1;
    }

    protected DbOperationRecordDto(
            StackTraceElementDto callerStackTraceElement,
            Long requestId,
            Long statementId,
            Integer executionTimeMs,
            OperationKind type) {
        this.callerStackTraceElement = callerStackTraceElement;
        this.requestId = requestId;
        this.statementId = statementId;
        this.executionTimeMs = executionTimeMs;
        this.type = type;
    }

    public OperationKind getType() {
        return type;
    }

    public void setType(OperationKind type) {
        this.type = type;
    }

    public StackTraceElementDto getCallerStackTraceElement() {
        return callerStackTraceElement;
    }

    public void setCallerStackTraceElement(StackTraceElementDto callerStackTraceElement) {
        this.callerStackTraceElement = callerStackTraceElement;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getStatementId() {
        return statementId;
    }

    public void setStatementId(Long statementId) {
        this.statementId = statementId;
    }

    public Integer getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(Integer executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }
}
