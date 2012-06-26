package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;

public interface DbOperationRecordProcessor {
    
    public void processDbOperationRecord(DbOperationRecordDTO record);

    public void displayNewDbOperationRecords();

    public void clearOperationRecords();

}
