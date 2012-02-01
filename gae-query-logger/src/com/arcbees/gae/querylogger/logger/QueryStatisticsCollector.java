package com.arcbees.gae.querylogger.logger;

import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class QueryStatisticsCollector implements QueryLogger {
    
    private static final int N_PLUS_ONE_THRESHOLD = 5;

    // TODO support wildcards, also externalize to configuration
    private static final String _IGNORED_PACKAGES[] = {
            "java.lang",
            "com.arcbees.gae.querylogger.logger",
            "com.google.appengine.api.datastore",
            "com.googlecode.objectify.impl"
    };

    // TODO add low hanging fruit kind of checks
    // * too many queries per request (say, 30)
    // * unbound queries

    // TODO the way we're storing query count data is too coarse grained, fix it
    private final MemcacheService memcacheService;

    private Set<String> ignoredPackages;
    
    private final String memcacheKey;

    @Inject
    public QueryStatisticsCollector(MemcacheService memcacheService, @Named("requestId") String requestId) {
        this.memcacheService = memcacheService;

        ignoredPackages = new HashSet<String>(_IGNORED_PACKAGES.length);
        Collections.addAll(ignoredPackages, _IGNORED_PACKAGES);
        
        memcacheKey = "queryCountDataByKind/" + requestId;
    }

    @Override
    public void logQuery(Query query, FetchOptions fetchOptions) {
        StackTraceElement caller = getCallerStackTraceElement();
        
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
    
    private StackTraceElement getCallerStackTraceElement() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (StackTraceElement currentElement : stackTrace) {
            String packageName = getStackTraceElementPackage(currentElement);
            if (!ignoredPackages.contains(packageName)) {
                return currentElement;
            }
        }

        return null;
    }

    private String getStackTraceElementPackage(StackTraceElement currentElement) {
        String className = currentElement.getClassName();
        int lastDotIndex = className.lastIndexOf('.');
        return lastDotIndex != -1 ? className.substring(0, lastDotIndex) : "";
    }

}

class QueryCountData implements Serializable {
    private static final long serialVersionUID = -182068789701427739L;
    Integer count = 0;
    Set<String> locations = new TreeSet<String>();
}
