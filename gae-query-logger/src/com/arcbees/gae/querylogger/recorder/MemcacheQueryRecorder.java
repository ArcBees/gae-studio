package com.arcbees.gae.querylogger.recorder;

import com.arcbees.gae.querylogger.common.dto.DbOperationRecord;
import com.arcbees.gae.querylogger.common.dto.DeleteRecord;
import com.arcbees.gae.querylogger.common.dto.GetRecord;
import com.arcbees.gae.querylogger.common.dto.PutRecord;
import com.arcbees.gae.querylogger.common.dto.QueryRecord;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.apphosting.api.DatastorePb.DeleteRequest;
import com.google.apphosting.api.DatastorePb.DeleteResponse;
import com.google.apphosting.api.DatastorePb.GetRequest;
import com.google.apphosting.api.DatastorePb.GetResponse;
import com.google.apphosting.api.DatastorePb.PutRequest;
import com.google.apphosting.api.DatastorePb.PutResponse;
import com.google.apphosting.api.DatastorePb.Query;
import com.google.apphosting.api.DatastorePb.QueryResult;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class MemcacheQueryRecorder implements QueryRecorder {
    
    private final Provider<MemcacheService> memcacheServiceProvider;
    
    private final Provider<String> requestIdProvider;
    
    private final StackInspector stackInspector;

    @Inject
    public MemcacheQueryRecorder(final Provider<MemcacheService> memcacheServiceProvider,
                                 final @Named("requestId") Provider<String> requestIdProvider,
                                 final StackInspector stackInspector) {
        this.memcacheServiceProvider = memcacheServiceProvider;
        this.requestIdProvider = requestIdProvider;
        this.stackInspector = stackInspector;
    }

    @Override
    public void recordDelete(DeleteRequest request, DeleteResponse response, int executionTimeMs) {
        recordOperation(new DeleteRecord(request, response, stackInspector.getCaller(), requestIdProvider.get(),
                executionTimeMs));
    }

    @Override
    public void recordGet(GetRequest request, GetResponse response, int executionTimeMs) {
        recordOperation(new GetRecord(request, response, stackInspector.getCaller(), requestIdProvider.get(),
                                      executionTimeMs));
    }

    @Override
    public void recordPut(PutRequest request, PutResponse response, int executionTimeMs) {
        recordOperation(new PutRecord(request, response, stackInspector.getCaller(), requestIdProvider.get(),
                                      executionTimeMs));
    }

    @Override
    public void recordQuery(Query query, QueryResult queryResult, int executionTimeMs) {
        recordOperation(new QueryRecord(query, queryResult, stackInspector.getCaller(), requestIdProvider.get(),
                executionTimeMs));
    }
    
    private void recordOperation(DbOperationRecord record) {
        memcacheServiceProvider.get().put("db.operation.record." + generateId(), record, Expiration.byDeltaSeconds(60));
    }

    private long generateId() {
        return memcacheServiceProvider.get().increment("db.operation.counter", 1L, 0L);
    }

}

