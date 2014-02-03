/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
