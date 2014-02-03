/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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

    protected DbOperationRecordDto(StackTraceElementDto callerStackTraceElement,
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
