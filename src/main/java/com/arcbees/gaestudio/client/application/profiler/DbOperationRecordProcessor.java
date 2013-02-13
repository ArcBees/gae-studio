package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;

public interface DbOperationRecordProcessor {

    public void processDbOperationRecord(DbOperationRecordDto record);

    public void displayNewDbOperationRecords();

    public void clearOperationRecords();

}
