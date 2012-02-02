/*
 * Copyright 2012 ArcBees Inc.
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

package com.arcbees.gae.querylogger.common.formatters;

import com.arcbees.gae.querylogger.common.dto.DbOperationRecord;
import com.arcbees.gae.querylogger.common.dto.GetRecord;
import com.arcbees.gae.querylogger.common.dto.PutRecord;
import com.arcbees.gae.querylogger.common.dto.QueryRecord;

public abstract class AbstractRecordFormatter implements RecordFormatter {

    @Override
    public String formatRecord(DbOperationRecord record) {
        if (record instanceof GetRecord) {
            return formatRecord((GetRecord)record);
        } else if (record instanceof PutRecord) {
            return formatRecord((PutRecord)record);
        } else if (record instanceof QueryRecord) {
            return formatRecord((QueryRecord)record);
        } else {
            throw new ClassCastException(
                    "Could not cast " + record.getClass().getCanonicalName() + " to a known record type");
        }
    }
    
}
