/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

public class RequestStatistics {

    private long requestId;
    private int statementCount;
    private int executionTimeMs;

    RequestStatistics(long requestId, int statementCount, int executionTimeMs) {
        this.requestId = requestId;
        this.statementCount = statementCount;
        this.executionTimeMs = executionTimeMs;
    }

    public long getRequestId() {
        return requestId;
    }

    public int getStatementCount() {
        return statementCount;
    }

    public void incrementStatementCount() {
        statementCount++;
    }

    public int getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void incrementExecutionTimeMs(int deltaTimeMs) {
        executionTimeMs += deltaTimeMs;
    }

}
