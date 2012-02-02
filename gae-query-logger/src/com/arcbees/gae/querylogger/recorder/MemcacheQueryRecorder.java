package com.arcbees.gae.querylogger.recorder;

import com.arcbees.gae.querylogger.common.QueryCountData;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.apphosting.api.DatastorePb;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.HashMap;
import java.util.Map;

public class MemcacheQueryRecorder implements QueryRecorder {
    
    // TODO the way we're storing query count data is too coarse grained, fix it
    private final MemcacheService memcacheService;

    private final String memcacheKey;
    
    private final StackInspector stackInspector;

    @Inject
    public MemcacheQueryRecorder(final MemcacheService memcacheService,
                                 final @Named("requestId") String requestId,
                                 final StackInspector stackInspector) {
        this.memcacheService = memcacheService;
        this.memcacheKey = "queryCountDataByKind/" + requestId;
        this.stackInspector = stackInspector;
    }

    @Override
    public void recordQuery(Query query, FetchOptions fetchOptions) {
        StackTraceElement caller = stackInspector.getCallerStackTraceElement();
        
        String kind = query.getKind();

        MemcacheService.IdentifiableValue queryCountDataByKindIdentifiable;
        Map<String, QueryCountData> queryCountDataByKind;

        do {
            queryCountDataByKindIdentifiable = memcacheService.getIdentifiable(memcacheKey);

            queryCountDataByKind = (queryCountDataByKindIdentifiable != null)
                    ? (Map<String, QueryCountData>) queryCountDataByKindIdentifiable.getValue()
                    : new HashMap<String, QueryCountData>();

            if (queryCountDataByKind == null) {
                queryCountDataByKind = new HashMap<String, QueryCountData>();
            }
            if (!queryCountDataByKind.containsKey(kind)) {
                queryCountDataByKind.put(kind, new QueryCountData());
            }
            QueryCountData queryCountData = queryCountDataByKind.get(kind);

            queryCountData.incrementCount();
            queryCountData.addLocation(caller.getFileName() + ":" + caller.getLineNumber());
            
            if (queryCountDataByKindIdentifiable == null) {
                if (memcacheService.put(memcacheKey, queryCountDataByKind, null,
                        MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT)) {
                    break;
                }
            }
        } while (queryCountDataByKindIdentifiable == null
                || !memcacheService.putIfUntouched(memcacheKey, queryCountDataByKindIdentifiable,
                        queryCountDataByKind));
    }

    @Override
    public void recordQuery(DatastorePb.Query query, DatastorePb.QueryResult result) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void recordGet(DatastorePb.GetRequest request, DatastorePb.GetResponse response) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void recordPut(DatastorePb.PutRequest request, DatastorePb.PutResponse response) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}

