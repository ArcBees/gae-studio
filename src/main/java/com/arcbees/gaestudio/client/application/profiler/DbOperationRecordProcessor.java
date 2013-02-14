package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;

public interface DbOperationRecordProcessor {
    void processDbOperationRecord(DbOperationRecordDto record);

    void displayNewDbOperationRecords();

    void clearOperationRecords();
}
