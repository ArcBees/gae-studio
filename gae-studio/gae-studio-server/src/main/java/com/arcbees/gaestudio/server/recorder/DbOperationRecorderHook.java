/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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

public class DbOperationRecorderHook extends BaseHook {
    private final DbOperationRecorder dbOperationRecorder;

    @Inject
    DbOperationRecorderHook(DbOperationRecorder dbOperationRecorder,
            @Assisted Delegate<Environment> baseDelegate) {
        super(baseDelegate);

        this.dbOperationRecorder = dbOperationRecorder;
    }

    @Override
    public byte[] makeSyncCall(Environment environment, String packageName, String methodName, byte[] request)
            throws ApiProxyException {
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

        PutResponse putResponse = new PutResponse();
        putResponse.mergeFrom(result);

        dbOperationRecorder.recordDbOperation(putRequest, putResponse, (int) (end - start));

        return result;
    }
}
