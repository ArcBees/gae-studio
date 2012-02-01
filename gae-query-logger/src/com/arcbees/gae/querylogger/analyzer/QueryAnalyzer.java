/*
 * Copyright 2011 ArcBees Inc.
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

package com.arcbees.gae.querylogger.analyzer;

import com.arcbees.gae.querylogger.common.QueryCountData;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryAnalyzer {

    private static final int N_PLUS_ONE_THRESHOLD = 5;

    // TODO add low hanging fruit kind of checks
    // * too many queries per request (say, 30)
    // * unbound queries

    // TODO the way we're storing query count data is too coarse grained, fix it
    private final MemcacheService memcacheService;

    private final String memcacheKey;

    @Inject
    public QueryAnalyzer(final MemcacheService memcacheService, final @Named("requestId") String requestId) {
        this.memcacheService = memcacheService;
        this.memcacheKey = "queryCountDataByKind/" + requestId;
    }

    public Iterable<String> getReport() {
        List<String> report = new ArrayList<String>();
        Map<String, QueryCountData> queryCountDataByKind =
                (Map<String, QueryCountData>) memcacheService.get(memcacheKey);

        for (String kind : queryCountDataByKind.keySet()) {
            StringBuilder builder = new StringBuilder();
            QueryCountData queryCountData = queryCountDataByKind.get(kind);

            if (queryCountData.getCount() >= N_PLUS_ONE_THRESHOLD) {
                builder.append("WARNING: Potential N+1 query for ");
                builder.append(kind);
                builder.append(".class, consider using a batched query instead.  Code location(s): ");
                boolean first = true;
                for (String location : queryCountData.getLocations()) {
                    if (first) first = false; else builder.append(", ");
                    builder.append(location);
                }
            }
            report.add(builder.toString());
        }

        return report;
    }

}
