/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.server.recorder.MemcacheKey;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsResult;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
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
    public GetNewDbOperationRecordsResult execute(GetNewDbOperationRecordsAction action, ExecutionContext context)
            throws ActionException {
        Long mostRecentId = getMostRecentId();
        if (mostRecentId == null) {
            logger.info("Could not find a mostRecentId");
            return new GetNewDbOperationRecordsResult(new ArrayList<DbOperationRecordDTO>());
        }

        long beginId = action.getLastId() + 1;
        // TODO if there is a big difference between beginId and the most recent id, binary search for the true start
        long endId = action.getMaxResults() != null
                ? Math.min(action.getLastId() + action.getMaxResults(), getMostRecentId())
                : getMostRecentId();

        if (beginId > endId) {
            logger.info("No new records since last poll");
            return new GetNewDbOperationRecordsResult(new ArrayList<DbOperationRecordDTO>());
        }

        logger.info("Attempting to retrieve ids " + beginId + "-" + endId);

        Map<String, Object> recordsByKey = memcacheService.getAll(getNewOperationRecordKeys(beginId, endId));
        // TODO trimming missing results only from the end of the range is incorrect, as there are scenarios
        // in which there could be missing records in the middle.  We need a better approach to this that
        // always retrieves all missing records.

        // TODO optimize this
        ArrayList<DbOperationRecordDTO> records = new ArrayList<DbOperationRecordDTO>(recordsByKey.size());
        for (Object recordObject : recordsByKey.values()) {
            records.add((DbOperationRecordDTO) recordObject);
        }

        return new GetNewDbOperationRecordsResult(records);
    }

    private List<String> getNewOperationRecordKeys(long beginId, long endId) {
        List<String> keys = new ArrayList<String>((int) (endId - beginId + 1));
        for (long i = beginId; i <= endId; ++i) {
            keys.add(MemcacheKey.DB_OPERATION_RECORD_PREFIX.getName() + i);
        }
        return keys;
    }

    private Long getMostRecentId() {
        return (Long) memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName());
    }

}
