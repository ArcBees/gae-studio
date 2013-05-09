/**
 * Copyright 2013 ArcBees Inc.
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

import com.arcbees.gaestudio.server.dto.mapper.QueryMapper;
import com.arcbees.gaestudio.server.dto.mapper.QueryResultMapper;
import com.arcbees.gaestudio.server.dto.mapper.StackTraceElementMapper;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.dto.DeleteRecordDTO;
import com.arcbees.gaestudio.shared.dto.GetRecordDto;
import com.arcbees.gaestudio.shared.dto.PutRecordDto;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDto;
import com.arcbees.gaestudio.shared.util.StackInspector;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.apphosting.api.DatastorePb;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

// TODO externalize magic literals
public class MemcacheDbOperationRecorder implements DbOperationRecorder {
    private final Provider<MemcacheService> memcacheServiceProvider;
    private final Provider<Long> requestIdProvider;
    private final StackInspector stackInspector;

    @Inject
    MemcacheDbOperationRecorder(Provider<MemcacheService> memcacheServiceProvider,
                                @Named("requestId") Provider<Long> requestIdProvider,
                                StackInspector stackInspector) {
        this.memcacheServiceProvider = memcacheServiceProvider;
        this.requestIdProvider = requestIdProvider;
        this.stackInspector = stackInspector;
    }

    @Override
    public void recordDbOperation(DatastorePb.DeleteRequest request, DatastorePb.DeleteResponse response,
                                  int executionTimeMs) {
        recordOperation(new DeleteRecordDTO(
                //request, response,
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }

    @Override
    public void recordDbOperation(DatastorePb.GetRequest request, DatastorePb.GetResponse response,
                                  int executionTimeMs) {
        recordOperation(new GetRecordDto(
                //request, response,
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }

    @Override
    public void recordDbOperation(DatastorePb.PutRequest request, DatastorePb.PutResponse response,
                                  int executionTimeMs) {
        recordOperation(new PutRecordDto(
                //request, response,
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }

    @Override
    public void recordDbOperation(DatastorePb.Query query, DatastorePb.QueryResult queryResult, int executionTimeMs) {
        recordOperation(new QueryRecordDto(
                QueryMapper.mapDTO(query), QueryResultMapper.mapDTO(queryResult),
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }

    private void recordOperation(DbOperationRecordDto record) {
        memcacheServiceProvider.get().put(MemcacheKey.DB_OPERATION_RECORD_PREFIX.getName() + record.getStatementId(),
                record,
                Expiration.byDeltaSeconds(60));
    }

    private long generateId() {
        return memcacheServiceProvider.get().increment(MemcacheKey.DB_OPERATION_COUNTER.getName(), 1L, 0L);
    }
}

