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

package com.arcbees.gae.querylogger.analyzer;

import com.arcbees.gae.querylogger.common.dto.DbOperationRecord;
import com.arcbees.gae.querylogger.common.dto.GetRecord;
import com.arcbees.gae.querylogger.common.dto.PutRecord;
import com.arcbees.gae.querylogger.common.dto.QueryRecord;
import com.arcbees.gae.querylogger.common.formatters.RecordFormatter;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class QueryAnalyzer {

    private static final int N_PLUS_ONE_THRESHOLD = 5;

    // TODO add low hanging fruit kind of checks
    // * too many queries per request (say, 30)
    // * unbound queries

    private final MemcacheService memcacheService;
    
    private final RecordFormatter recordFormatter;
    
    private Map<String, List<DbOperationRecord>> recordsByKind;

    private long lastId;

    @Inject
    public QueryAnalyzer(final MemcacheService memcacheService, final RecordFormatter recordFormatter) {
        this.memcacheService = memcacheService;
        this.recordFormatter = recordFormatter;
        this.recordsByKind = new HashMap<String, List<DbOperationRecord>>();
        this.lastId = -1;
    }

    public Iterable<String> getReport() {
        getNewOperationRecords();
        
        List<String> report = new ArrayList<String>();

        for (String kind : recordsByKind.keySet()) {
            List<DbOperationRecord> operations = recordsByKind.get(kind);

            for (DbOperationRecord record : operations) {
                StringBuilder builder = new StringBuilder();
                
                builder.append(recordFormatter.formatRecord(record));
                builder.append(" (");
                builder.append(record.getExecutionTimeMs());
                builder.append("ms)");
                
                if (record instanceof QueryRecord) {
                    QueryRecord queryRecord = (QueryRecord)record;
                    int count = queryRecord.getQueryResult().resultSize();
                    
                    builder.append(" (");
                    builder.append(count);
                    builder.append(count == 1 ? " result)" : " results)");
                }
                
                report.add(builder.toString());
            }

            if (operations.size() >= N_PLUS_ONE_THRESHOLD) {
                StringBuilder builder = new StringBuilder();

                builder.append("WARNING: Potential N+1 query for ");
                builder.append(kind);
                builder.append(".class, consider using a batched query instead.  Code location(s): ");

                Set<String> locations = new TreeSet<String>();
                for (DbOperationRecord record : operations) {
                    locations.add(record.getCaller().getFileName() + ":" + record.getCaller().getLineNumber());
                }
                
                boolean first = true;
                for (String loc : locations) {
                    if (first) first = false; else builder.append(", ");
                    builder.append(loc);
                }
                
                report.add(builder.toString());
            }
            
        }

        return report;
    }

    private void getNewOperationRecords() {
        Map<String, Object> recordsByKey =
                new TreeMap<String, Object>(memcacheService.getAll(getNewOperationRecordKeys()));

        for (Object recordObject : recordsByKey.values()) {
            DbOperationRecord record = (DbOperationRecord)recordObject;
            // TODO figure out what to do with this.  The corner case is heterogeneous batch gets
            String kind = "<kind extraction not implemented yet>";
            if (!recordsByKind.containsKey(kind)) {
                recordsByKind.put(kind, new ArrayList<DbOperationRecord>());
            }
            recordsByKind.get(kind).add(record);
        }
    }
    
    private List<String> getNewOperationRecordKeys() {
        long mostRecentId = getMostRecentId();
        List<String> keys = new ArrayList<String>((int)(mostRecentId - lastId));
        for (long i = lastId + 1; i <= mostRecentId; ++i) {
            keys.add("db.operation.record." + i);
        }
        lastId = mostRecentId;
        return keys;
    }
    
    private long getMostRecentId() {
        return (Long)memcacheService.get("db.operation.counter");
    }
    
}
