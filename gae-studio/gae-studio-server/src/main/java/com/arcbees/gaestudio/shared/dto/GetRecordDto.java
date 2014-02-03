/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto;

import com.arcbees.gaestudio.shared.dto.stacktrace.StackTraceElementDto;

public class GetRecordDto extends DbOperationRecordDto {
    @SuppressWarnings("unused")
    public GetRecordDto() {
    }

    public GetRecordDto(StackTraceElementDto callerStackTraceElement,
                        Long requestId,
                        Long statementId,
                        Integer executionTimeMs) {
        super(callerStackTraceElement, requestId, statementId, executionTimeMs, OperationKind.GET);
    }
}
