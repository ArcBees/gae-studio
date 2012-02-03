/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsResult;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
import com.google.appengine.api.memcache.MemcacheService;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

// TODO externalize magic strings
// TODO add logging
public class GetNewDbOperationRecordsHandler
        extends AbstractActionHandler<GetNewDbOperationRecordsAction, GetNewDbOperationRecordsResult> {

    private final Logger logger;
    
    private final MemcacheService memcacheService;

    public GetNewDbOperationRecordsHandler(final Logger logger, final MemcacheService memcacheService) {
        super(GetNewDbOperationRecordsAction.class);

        this.logger = logger;
        this.memcacheService = memcacheService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public GetNewDbOperationRecordsResult execute(GetNewDbOperationRecordsAction action, ExecutionContext context)
            throws ActionException {

        long beginId = action.getLastId() + 1;
        long endId = action.getMaxResults() != null
                ? Math.min(action.getLastId() + action.getMaxResults(), getMostRecentId())
                : getMostRecentId();

        Map<String, Object> recordsByKey = memcacheService.getAll(getNewOperationRecordKeys(beginId, endId));
        // TODO trimming missing results only from the end of the range is incorrect, as there are scenarios
        // in which there could be missing records in the middle.  We need a better approach to this that
        // always retrieves all missing records.
        while (!recordsByKey.containsKey("db.operation.record." + endId)) {
            endId--;
        }

        Iterable<DbOperationRecord> records = (Iterable<DbOperationRecord>)(Iterable<?>)recordsByKey.values();
        
        return new GetNewDbOperationRecordsResult(endId, records);
    }

    @Override
    public void undo(GetNewDbOperationRecordsAction action, GetNewDbOperationRecordsResult result,
                     ExecutionContext context)
            throws ActionException {
        // Nothing to do here
    }

    private List<String> getNewOperationRecordKeys(long beginId, long endId) {
        List<String> keys = new ArrayList<String>((int)(endId - beginId + 1));
        for (long i = beginId; i <= endId; ++i) {
            keys.add("db.operation.record." + i);
        }
        return keys;
    }

    private long getMostRecentId() {
        return (Long)memcacheService.get("db.operation.counter");
    }

}
