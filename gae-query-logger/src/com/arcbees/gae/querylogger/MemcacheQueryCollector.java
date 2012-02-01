package com.arcbees.gae.querylogger;

import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MemcacheQueryCollector implements QueryCollector {
    
    private static final int N_PLUS_ONE_THRESHOLD = 5;

    // TODO add low hanging fruit kind of checks
    // * too many queries per request (say, 30)
    // * unbound queries

    // TODO the way we're storing query count data is too coarse grained, fix it
    private final MemcacheService memcacheService;

    private final String memcacheKey;
    
    private final StackInspector stackInspector;

    @Inject
    public MemcacheQueryCollector(final MemcacheService memcacheService,
                                  final @Named("requestId") String requestId,
                                  final StackInspector stackInspector) {
        this.memcacheService = memcacheService;
        this.memcacheKey = "queryCountDataByKind/" + requestId;
        this.stackInspector = stackInspector;
    }

    @Override
    public void logQuery(Query query, FetchOptions fetchOptions) {
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

            queryCountData.count++;
            queryCountData.locations.add(caller.getFileName() + ":" + caller.getLineNumber());
            
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

    public void printReport() {
        Map<String, QueryCountData> queryCountDataByKind =
                (Map<String, QueryCountData>) memcacheService.get(memcacheKey);

        for (String kind : queryCountDataByKind.keySet()) {
            QueryCountData queryCountData = queryCountDataByKind.get(kind);
            
            if (queryCountData.count >= N_PLUS_ONE_THRESHOLD) {
                StringBuilder builder = new StringBuilder();
                
                builder.append("WARNING: Potential N+1 query for ");
                builder.append(kind);
                builder.append(".class, consider using a batched query instead.  Code location(s): ");
                boolean first = true;
                for (String location : queryCountData.locations) {
                    if (first) first = false; else builder.append(", ");
                    builder.append(location);
                }
                System.out.println(builder.toString());
            }
        }
    }

}

class QueryCountData implements Serializable {
    private static final long serialVersionUID = -182068789701427739L;
    Integer count = 0;
    Set<String> locations = new TreeSet<String>();
}
