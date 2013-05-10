/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import java.util.ArrayList;
import java.util.List;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;

public class FilterValue<T> {
    private T key;
    private int executionTimeMs = 0;
    private final List<DbOperationRecordDto> statements = new ArrayList<DbOperationRecordDto>();

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
