/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsResult;
import com.arcbees.gaestudio.shared.dto.DbOperationRecord;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.inject.Inject;
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

    @Inject
    public GetNewDbOperationRecordsHandler(final Logger logger, final MemcacheService memcacheService) {
        super(GetNewDbOperationRecordsAction.class);

        this.logger = logger;
        this.memcacheService = memcacheService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public GetNewDbOperationRecordsResult execute(GetNewDbOperationRecordsAction action, ExecutionContext context)
            throws ActionException {
        
        Long mostRecentId = getMostRecentId();
        if (mostRecentId == null) {
            logger.info("Could not find a mostRecentId");
            return new GetNewDbOperationRecordsResult(action.getLastId(), new ArrayList<DbOperationRecord>());
        }

        long beginId = action.getLastId() + 1;
        long endId = action.getMaxResults() != null
                ? Math.min(action.getLastId() + action.getMaxResults(), getMostRecentId())
                : getMostRecentId();
        
        if (beginId > endId) {
            logger.info("No new records since last poll");
            return new GetNewDbOperationRecordsResult(action.getLastId(), new ArrayList<DbOperationRecord>());
        }
        
        logger.info("Attempting to retrieve ids " + beginId + "-" + endId);

        Map<String, Object> recordsByKey = memcacheService.getAll(getNewOperationRecordKeys(beginId, endId));
        // TODO trimming missing results only from the end of the range is incorrect, as there are scenarios
        // in which there could be missing records in the middle.  We need a better approach to this that
        // always retrieves all missing records.
        // TODO actually this might best be entirely left to the client, since it needs to scan the records anyway
        while (!recordsByKey.containsKey("db.operation.record." + endId)) {
            endId--;
        }
        
        logger.info("Retrieved " + recordsByKey.size() + " records, endId is now " + endId);

        // TODO optimize this
        ArrayList<DbOperationRecord> records = new ArrayList<DbOperationRecord>(recordsByKey.size());
        for (Object recordObject : recordsByKey.values()) {
            records.add((DbOperationRecord)recordObject);
        }
        
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

    private Long getMostRecentId() {
        return (Long)memcacheService.get("db.operation.counter");
    }

}
