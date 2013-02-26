/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

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
