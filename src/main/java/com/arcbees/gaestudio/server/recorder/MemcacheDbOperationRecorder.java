package com.arcbees.gaestudio.server.recorder;

import com.arcbees.gaestudio.server.dto.mapper.QueryMapper;
import com.arcbees.gaestudio.server.dto.mapper.QueryResultMapper;
import com.arcbees.gaestudio.server.dto.mapper.StackTraceElementMapper;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.arcbees.gaestudio.shared.dto.DeleteRecordDTO;
import com.arcbees.gaestudio.shared.dto.GetRecordDTO;
import com.arcbees.gaestudio.shared.dto.PutRecordDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDTO;
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
    public MemcacheDbOperationRecorder(final Provider<MemcacheService> memcacheServiceProvider,
                                       final @Named("requestId") Provider<Long> requestIdProvider,
                                       final StackInspector stackInspector) {
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
        recordOperation(new GetRecordDTO(
                //request, response,
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }

    @Override
    public void recordDbOperation(DatastorePb.PutRequest request, DatastorePb.PutResponse response,
                                  int executionTimeMs) {
        recordOperation(new PutRecordDTO(
                //request, response,
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }

    @Override
    public void recordDbOperation(DatastorePb.Query query, DatastorePb.QueryResult queryResult, int executionTimeMs) {
        recordOperation(new QueryRecordDTO(
                QueryMapper.mapDTO(query), QueryResultMapper.mapDTO(queryResult),
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }
    
    private void recordOperation(DbOperationRecordDTO record) {
        memcacheServiceProvider.get().put(MemcacheKey.DB_OPERATION_RECORD_PREFIX.getName() + record.getStatementId(), record,
                Expiration.byDeltaSeconds(60));
    }

    private long generateId() {
        return memcacheServiceProvider.get().increment(MemcacheKey.DB_OPERATION_COUNTER.getName(), 1L, 0L);
    }

}

