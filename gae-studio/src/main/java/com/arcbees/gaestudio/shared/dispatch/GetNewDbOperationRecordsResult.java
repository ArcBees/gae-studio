package com.arcbees.gaestudio.shared.dispatch;

import java.util.ArrayList;

import com.gwtplatform.dispatch.shared.Result;

public class GetNewDbOperationRecordsResult implements Result {
    private ArrayList<com.arcbees.gaestudio.shared.dto.DbOperationRecordDto> records;

    protected GetNewDbOperationRecordsResult() {
        // Possibly for serialization.
    }

    public GetNewDbOperationRecordsResult(ArrayList<com.arcbees.gaestudio.shared.dto.DbOperationRecordDto> records) {
        this.records = records;
    }

    public ArrayList<com.arcbees.gaestudio.shared.dto.DbOperationRecordDto> getRecords() {
        return records;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GetNewDbOperationRecordsResult other = (GetNewDbOperationRecordsResult) obj;
        if (records == null) {
            if (other.records != null)
                return false;
        } else if (!records.equals(other.records))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (records == null ? 1 : records.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "GetNewDbOperationRecordsResult[" + records + "]";
    }
}
