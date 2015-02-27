/**
 * Copyright 2015 ArcBees Inc.
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

package com.arcbees.gaestudio.client.formatters;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.dto.DeleteRecordDto;
import com.arcbees.gaestudio.shared.dto.GetRecordDto;
import com.arcbees.gaestudio.shared.dto.PutRecordDto;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDto;

public abstract class AbstractRecordFormatter implements RecordFormatter {
    @Override
    public String formatRecord(DbOperationRecordDto record) {
        if (record instanceof DeleteRecordDto) {
            return formatRecord((DeleteRecordDto) record);
        } else if (record instanceof GetRecordDto) {
            return formatRecord((GetRecordDto) record);
        } else if (record instanceof PutRecordDto) {
            return formatRecord((PutRecordDto) record);
        } else if (record instanceof QueryRecordDto) {
            return formatRecord((QueryRecordDto) record);
        } else {
            throw new ClassCastException("Could not cast " + record.getClass().getName() + " to a known record type");
        }
    }
}
