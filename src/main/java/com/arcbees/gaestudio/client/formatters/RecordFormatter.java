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

public interface RecordFormatter {
    
    public String formatRecord(DeleteRecordDTO record);
    
    public String formatRecord(GetRecordDTO record);

    public String formatRecord(PutRecordDTO record);

    public String formatRecord(QueryRecordDTO record);
    
    public String formatRecord(DbOperationRecordDTO record);

}
