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

package com.arcbees.gaestudio.server.recorder;

import com.google.apphosting.api.ApiProxy.ApiProxyException;
import com.google.apphosting.api.ApiProxy.Delegate;
import com.google.apphosting.api.ApiProxy.Environment;
import com.google.apphosting.api.DatastorePb.DeleteRequest;
import com.google.apphosting.api.DatastorePb.DeleteResponse;
import com.google.apphosting.api.DatastorePb.GetRequest;
import com.google.apphosting.api.DatastorePb.GetResponse;
import com.google.apphosting.api.DatastorePb.PutRequest;
import com.google.apphosting.api.DatastorePb.PutResponse;
import com.google.apphosting.api.DatastorePb.Query;
import com.google.apphosting.api.DatastorePb.QueryResult;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.util.logging.Logger;

public class DbOperationRecorderHook extends BaseHook {
    private final Logger logger;

    private final DbOperationRecorder dbOperationRecorder;

    @Inject
    public DbOperationRecorderHook(final Logger logger, final DbOperationRecorder dbOperationRecorder,
                                   @Assisted Delegate<Environment> baseDelegate) {
        super(baseDelegate);
        this.logger = logger;
        this.dbOperationRecorder = dbOperationRecorder;
    }

    @Override
    public byte[] makeSyncCall(Environment environment, String packageName, String methodName, byte[] request)
            throws ApiProxyException {
        logger.info("Processing makeSyncCall for " + packageName + "." + methodName);

        if ("Delete".equals(methodName)) {
            return this.handleDelete(environment, request);
        } else if ("Get".equals(methodName)) {
            return this.handleGet(environment, request);
        } else if ("Put".equals(methodName)) {
            return this.handlePut(environment, request);
        } else if ("RunQuery".equals(methodName)) {
            return this.handleQuery(environment, request);
        } else {
            return getBaseDelegate().makeSyncCall(environment, packageName, methodName, request);
        }
    }

    private byte[] handleDelete(Environment environment, byte[] requestData) {
        DeleteRequest request = new DeleteRequest();
        request.mergeFrom(requestData);

        Delegate<Environment> d = getBaseDelegate();
        long start = System.currentTimeMillis();
        byte[] result = d.makeSyncCall(environment, "datastore_v3", "Delete", requestData);
        long end = System.currentTimeMillis();

        logger.info("Executed Get in " + (end - start) + "ms");

        DeleteResponse response = new DeleteResponse();
        response.mergeFrom(result);

        dbOperationRecorder.recordDbOperation(request, response, (int) (end - start));

        return result;
    }
    
    private byte[] handleQuery(Environment environment, byte[] request) {
        Query query = new Query();
        query.mergeFrom(request);

        Delegate<Environment> d = getBaseDelegate();
        long start = System.currentTimeMillis();
        byte[] result = d.makeSyncCall(environment, "datastore_v3", "RunQuery", request);
        long end = System.currentTimeMillis();

        logger.info("Executed RunQuery in " + (end - start) + "ms");

        QueryResult queryResult = new QueryResult();
        queryResult.mergeFrom(result);
        
        dbOperationRecorder.recordDbOperation(query, queryResult, (int) (end - start));

        return result;
    }

    private byte[] handleGet(Environment environment, byte[] request) {
        GetRequest getRequest = new GetRequest();
        getRequest.mergeFrom(request);

        Delegate<Environment> d = getBaseDelegate();
        long start = System.currentTimeMillis();
        byte[] result = d.makeSyncCall(environment, "datastore_v3", "Get", request);
        long end = System.currentTimeMillis();

        logger.info("Executed Get in " + (end - start) + "ms");

        GetResponse getResponse = new GetResponse();
        getResponse.mergeFrom(result);

        dbOperationRecorder.recordDbOperation(getRequest, getResponse, (int) (end - start));

        return result;
    }

    private byte[] handlePut(Environment environment, byte[] request) {
        PutRequest putRequest = new PutRequest();
        putRequest.mergeFrom(request);

        Delegate<Environment> d = getBaseDelegate();
        long start = System.currentTimeMillis();
        byte[] result = d.makeSyncCall(environment, "datastore_v3", "Put", request);
        long end = System.currentTimeMillis();

        logger.info("Executed Put in " + (end - start) + "ms");
        
        PutResponse putResponse = new PutResponse();
        putResponse.mergeFrom(result);

        dbOperationRecorder.recordDbOperation(putRequest, putResponse, (int) (end - start));

        return result;
    }
}
