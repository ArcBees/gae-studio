package com.arcbees.gae.querylogger;

import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class QueryCollector {
    
    private static final int N_PLUS_ONE_THRESHOLD = 5;

    // TODO support wildcards, also externalize to configuration
    private static final String _IGNORED_PACKAGES[] = {
            "java.lang",
            "com.arcbees.gae.querylogger",
            "com.google.appengine.api.datastore",
            "com.googlecode.objectify.impl"
    };

    // TODO add low hanging fruit kind of checks
    // * too many queries per request (say, 30)
    // * unbound queries

    private Map<String, QueryCountData> queryCountDataByKind;
    
    private Set<String> ignoredPackages;
    
    public QueryCollector() {
        queryCountDataByKind = new HashMap<String, QueryCountData>();
        ignoredPackages = new HashSet<String>(_IGNORED_PACKAGES.length);
        Collections.addAll(ignoredPackages, _IGNORED_PACKAGES);
    }

    public void collectQuery(Query query, FetchOptions fetchOptions) {
        StackTraceElement caller = getCallerStackTraceElement();
        
        String kind = query.getKind();
        if (!queryCountDataByKind.containsKey(kind)) {
            queryCountDataByKind.put(kind, new QueryCountData());
        }
        QueryCountData queryCountData = queryCountDataByKind.get(kind);
        
        queryCountData.count++;
        queryCountData.locations.add(caller.getFileName() + ":" + caller.getLineNumber());
    }

    public void printReport() {
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

    class QueryCountData {
        Integer count = 0;
        Set<String> locations = new TreeSet<String>();
    }

}
