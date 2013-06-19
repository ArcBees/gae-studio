/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto;

//import com.google.apphosting.api.DatastorePb.DeleteRequest;
//import com.google.apphosting.api.DatastorePb.DeleteResponse;

import com.arcbees.gaestudio.shared.stacktrace.StackTraceElementDto;

// TODO : Complete
public class DeleteRecordDTO extends DbOperationRecordDto {
    private static final long serialVersionUID = 3745865294476274476L;

//    private final DeleteRequest deleteRequest;
//    private final DeleteResponse deleteResponse;

    @SuppressWarnings("unused")
    protected DeleteRecordDTO() {
    }

    public DeleteRecordDTO(//DeleteRequest deleteRequest, DeleteResponse deleteResponse,
                           StackTraceElementDto callerStackTraceElement,
                           Long requestId, Long statementId, Integer executionTimeMs) {
        super(callerStackTraceElement, requestId, statementId, executionTimeMs);
//        this.deleteRequest = deleteRequest;
//        this.deleteResponse = deleteResponse;
    }

//    public DeleteRequest getDeleteRequest() {
//        return deleteRequest;
//    }
//
//    public DeleteResponse getDeleteResponse() {
//        return deleteResponse;
//    }
}
