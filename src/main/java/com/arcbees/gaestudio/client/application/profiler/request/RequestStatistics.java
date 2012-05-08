/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.request;

public class RequestStatistics {

    private long requestId;
    private int statementCount;
    private int executionTimeMs;

    RequestStatistics(long requestId, int statementCount, int executionTimeMs) {
        this.requestId = requestId;
        this.statementCount = statementCount;
        this.executionTimeMs = executionTimeMs;
    }

    long getRequestId() {
        return requestId;
    }

    int getStatementCount() {
        return statementCount;
    }

    void incrementStatementCount() {
        statementCount++;
    }

    int getExecutionTimeMs() {
        return executionTimeMs;
    }

    void incrementExecutionTimeMs(int deltaTimeMs) {
        executionTimeMs += deltaTimeMs;
    }

}
