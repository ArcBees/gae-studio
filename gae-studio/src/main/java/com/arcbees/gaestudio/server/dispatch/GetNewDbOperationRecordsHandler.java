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

package com.arcbees.gaestudio.server.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.arcbees.gaestudio.server.recorder.MemcacheKey;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsResult;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

// TODO externalize magic strings
// TODO add logging
public class GetNewDbOperationRecordsHandler extends
        AbstractActionHandler<GetNewDbOperationRecordsAction, GetNewDbOperationRecordsResult> {
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
