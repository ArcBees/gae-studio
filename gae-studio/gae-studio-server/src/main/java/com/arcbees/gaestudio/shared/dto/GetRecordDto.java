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
//import com.google.apphosting.api.DatastorePb.GetRequest;
//import com.google.apphosting.api.DatastorePb.GetResponse;

import com.arcbees.gaestudio.shared.stacktrace.StackTraceElementDto;

// TODO : Complete
public class GetRecordDto extends DbOperationRecordDto {
    private static final long serialVersionUID = -7824011424844857721L;

//    private final GetRequest getRequest;
//    private final GetResponse getResponse;

    @SuppressWarnings("unused")
    protected GetRecordDto() {
    }

    public GetRecordDto(//GetRequest getRequest, GetResponse getResponse,
                        StackTraceElementDto callerStackTraceElement,
                        Long requestId,
                        Long statementId,
                        Integer executionTimeMs) {
        super(callerStackTraceElement, requestId, statementId, executionTimeMs);
//        this.getRequest = getRequest;
//        this.getResponse = getResponse;
    }

//    public GetRequest getGetRequest() {
//        return getRequest;
//    }
//
//    public GetResponse getGetResponse() {
//        return getResponse;
//    }
}
