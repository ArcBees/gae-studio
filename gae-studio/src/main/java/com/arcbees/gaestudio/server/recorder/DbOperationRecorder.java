/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.recorder;

import com.google.apphosting.api.DatastorePb.*;

public interface DbOperationRecorder {
    void recordDbOperation(DeleteRequest request, DeleteResponse response, int executionTimeMs);

    void recordDbOperation(GetRequest request, GetResponse response, int executionTimeMs);

    void recordDbOperation(PutRequest request, PutResponse response, int executionTimeMs);

    void recordDbOperation(Query query, QueryResult queryResult, int executionTimeMs);
}
