/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto;

import com.arcbees.gaestudio.shared.stacktrace.StackTraceElementDto;

import java.io.Serializable;

public abstract class DbOperationRecordDto implements Serializable {
    private StackTraceElementDto callerStackTraceElement;

    private Long requestId;
    private Long statementId;
    private Integer executionTimeMs;

    @SuppressWarnings("unused")
    protected DbOperationRecordDto() {
        this.requestId = -1L;
        this.statementId = -1L;
        this.executionTimeMs = -1;
    }

    protected DbOperationRecordDto(StackTraceElementDto callerStackTraceElement, Long requestId, Long statementId,
                                   Integer executionTimeMs) {
        this.callerStackTraceElement = callerStackTraceElement;
        this.requestId = requestId;
        this.statementId = statementId;
        this.executionTimeMs = executionTimeMs;
    }

    public StackTraceElementDto getCallerStackTraceElement() {
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
