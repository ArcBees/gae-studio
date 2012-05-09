/*
 * Copyright 2012 ArcBees Inc.
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

import com.arcbees.gaestudio.shared.stacktrace.StackTraceElementDTO;

import java.io.Serializable;

public abstract class DbOperationRecordDTO implements Serializable {

    private StackTraceElementDTO callerStackTraceElement;
    
    private Long requestId;
    
    private Long statementId;
    
    private Integer executionTimeMs;

    @SuppressWarnings("unused")
    protected DbOperationRecordDTO() {
        this.requestId = -1L;
        this.statementId = -1L;
        this.executionTimeMs = -1;
    }

    protected DbOperationRecordDTO(StackTraceElementDTO callerStackTraceElement,
                                   Long requestId, Long statementId, Integer executionTimeMs) {
        this.callerStackTraceElement = callerStackTraceElement;
        this.requestId = requestId;
        this.statementId = statementId;
        this.executionTimeMs = executionTimeMs;
    }

    public StackTraceElementDTO getCallerStackTraceElement() {
        return callerStackTraceElement;
    }

    public Long getRequestId() {
        return requestId;
    }
    
    public Long getStatementId() {
        return statementId;
    }

    public Integer getExecutionTimeMs() {
        return executionTimeMs;
    }

}
