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

package com.arcbees.gaestudio.shared.dto;
//
//import com.google.apphosting.api.DatastorePb.PutRequest;
//import com.google.apphosting.api.DatastorePb.PutResponse;

public class PutRecord extends DbOperationRecord {

    private static final long serialVersionUID = 3745865294476274476L;
    
//    private final PutRequest putRequest;
//
//    private final PutResponse putResponse;

    @SuppressWarnings("unused")
    protected PutRecord() {
    }

    public PutRecord(//PutRequest putRequest, PutResponse putResponse,
                     //StackTraceElement[] stackTrace,
                     Long requestId,
                     Long statementId,
                     Integer executionTimeMs) {
        super(//stackTrace,
                requestId, statementId, executionTimeMs);
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