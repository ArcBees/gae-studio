/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto;
//
//import com.google.apphosting.api.DatastorePb.PutRequest;
//import com.google.apphosting.api.DatastorePb.PutResponse;

import com.arcbees.gaestudio.shared.stacktrace.StackTraceElementDto;

// TODO : Complete
public class PutRecordDto extends DbOperationRecordDto {
    private static final long serialVersionUID = 3745865294476274476L;

//    private final PutRequest putRequest;
//
//    private final PutResponse putResponse;

    @SuppressWarnings("unused")
    protected PutRecordDto() {
    }

    public PutRecordDto(//PutRequest putRequest, PutResponse putResponse,
                        StackTraceElementDto callerStackTraceElement,
                        Long requestId,
                        Long statementId,
                        Integer executionTimeMs) {
        super(callerStackTraceElement, requestId, statementId, executionTimeMs);
//        this.putRequest = putRequest;
//        this.putResponse = putResponse;
    }

//    public PutRequest getPutRequest() {
//        return putRequest;
//    }
//
//    public PutResponse getPutResponse() {
//        return putResponse;
//    }
}
