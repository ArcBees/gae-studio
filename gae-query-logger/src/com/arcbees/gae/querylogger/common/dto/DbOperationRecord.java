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

package com.arcbees.gae.querylogger.common.dto;

import java.io.Serializable;

public abstract class DbOperationRecord implements Serializable {

    private final StackTraceElement caller;
    
    private final String requestId;
    
    private final int executionTimeMs;

    protected DbOperationRecord(StackTraceElement caller, String requestId, int executionTimeMs) {
        this.caller = caller;
        this.requestId = requestId;
        this.executionTimeMs = executionTimeMs;
    }

    public StackTraceElement getCaller() {
        return caller;
    }

    public String getRequestId() {
        return requestId;
    }

    public int getExecutionTimeMs() {
        return executionTimeMs;
    }

}
