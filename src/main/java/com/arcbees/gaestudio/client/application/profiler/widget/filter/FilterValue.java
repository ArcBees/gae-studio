/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;

import java.util.ArrayList;
import java.util.List;

public class FilterValue<T> {

    private T key;
    private int executionTimeMs = 0;
    private final List<DbOperationRecordDTO> statements = new ArrayList<DbOperationRecordDTO>();

    protected FilterValue(T key) {
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    public int getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void addRecord(DbOperationRecordDTO record) {
        statements.add(record);
        executionTimeMs += record.getExecutionTimeMs();
    }

    public List<DbOperationRecordDTO> getStatements() {
        return statements;
    }

    public int getStatementCount() {
        return statements.size();
    }

}
