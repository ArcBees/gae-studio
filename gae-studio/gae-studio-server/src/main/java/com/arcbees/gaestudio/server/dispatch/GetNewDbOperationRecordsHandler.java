/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.server.recorder.MemcacheKey;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsResult;
import com.arcbees.gaestudio.server.dto.DbOperationRecordDto;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

// TODO externalize magic strings
// TODO add logging
public class GetNewDbOperationRecordsHandler
        extends AbstractActionHandler<GetNewDbOperationRecordsAction, GetNewDbOperationRecordsResult> {
    private static final String GET_NEW_DB_OPERATION_RECORD = "Get New Db Operation Record";

    private final Logger logger;
    private final GoogleAnalytic googleAnalytic;
    private final MemcacheService memcacheService;

    @Inject
    GetNewDbOperationRecordsHandler(Logger logger,
                                    GoogleAnalytic googleAnalytic,
                                    MemcacheService memcacheService) {
        super(GetNewDbOperationRecordsAction.class);

        this.logger = logger;
        this.googleAnalytic = googleAnalytic;
        this.memcacheService = memcacheService;
    }

    @Override
    public GetNewDbOperationRecordsResult execute(GetNewDbOperationRecordsAction action,
                                                  ExecutionContext context) throws ActionException {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_NEW_DB_OPERATION_RECORD);

        Long mostRecentId = getMostRecentId();
        if (mostRecentId == null) {
            logger.info("Could not find a mostRecentId");
            return new GetNewDbOperationRecordsResult(new ArrayList<DbOperationRecordDto>());
        }

        long beginId = action.getLastId() + 1;
        // TODO if there is a big difference between beginId and the most recent id, binary search for the true start
        long endId = action.getMaxResults() != null ? Math.min(action.getLastId() + action.getMaxResults(),
                getMostRecentId()) : getMostRecentId();

        if (beginId > endId) {
            logger.info("No new records since last poll");
            return new GetNewDbOperationRecordsResult(new ArrayList<DbOperationRecordDto>());
        }

        logger.info("Attempting to retrieve ids " + beginId + "-" + endId);

        Map<String, Object> recordsByKey = memcacheService.getAll(getNewOperationRecordKeys(beginId, endId));
        // TODO trimming missing results only from the end of the range is incorrect, as there are scenarios
        // in which there could be missing records in the middle. We need a better approach to this that
        // always retrieves all missing records.

        // TODO optimize this
        ArrayList<DbOperationRecordDto> records = new ArrayList<DbOperationRecordDto>(recordsByKey.size());
        for (Object recordObject : recordsByKey.values()) {
            records.add((DbOperationRecordDto) recordObject);
        }

        return new GetNewDbOperationRecordsResult(records);
    }

    private List<String> getNewOperationRecordKeys(long beginId,
                                                   long endId) {
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
