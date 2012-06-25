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

package com.arcbees.gaestudio.client.formatters;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.arcbees.gaestudio.shared.dto.DeleteRecordDTO;
import com.arcbees.gaestudio.shared.dto.GetRecordDTO;
import com.arcbees.gaestudio.shared.dto.PutRecordDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDTO;

public abstract class AbstractRecordFormatter implements RecordFormatter {

    @Override
    public String formatRecord(DbOperationRecordDTO record) {
        if (record instanceof DeleteRecordDTO) {
            return formatRecord((DeleteRecordDTO)record);
        } else if (record instanceof GetRecordDTO) {
            return formatRecord((GetRecordDTO)record);
        } else if (record instanceof PutRecordDTO) {
            return formatRecord((PutRecordDTO)record);
        } else if (record instanceof QueryRecordDTO) {
            return formatRecord((QueryRecordDTO)record);
        } else {
            throw new ClassCastException(
                    "Could not cast " + record.getClass().getName() + " to a known record type");
        }
    }
    
}
