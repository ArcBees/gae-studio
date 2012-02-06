package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.shared.dto.DbOperationRecord;

public interface DbOperationRecordProcessor {
    
    public void processDbOperationRecord(DbOperationRecord record);

    public void displayNewDbOperationRecords();

}
