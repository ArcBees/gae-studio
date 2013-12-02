/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.recorder.MemcacheKey;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.appengine.api.memcache.MemcacheService;

public class OperationServiceImpl implements OperationService {
    private final MemcacheService memcacheService;

    @Inject
    OperationServiceImpl(MemcacheService memcacheService) {
        this.memcacheService = memcacheService;
    }

    @Override
    public Long getMostRecentId() {
        return (Long) memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName());
    }

    public List<DbOperationRecordDto> getOperations(Long lastId, Integer limit) {
        Long mostRecentId = getMostRecentId();

        if (mostRecentId == null) {
            return null;
        }

        long beginId = lastId + 1;
        // TODO if there is a big difference between beginId and the most recent id, binary search for the true start
        long endId = limit != null ? Math.min(lastId + limit, mostRecentId) : mostRecentId;

        if (beginId > endId) {
            return null;
        }

        Map<String, Object> recordsByKey = memcacheService.getAll(getNewOperationRecordKeys(beginId, endId));
        // TODO trimming missing results only from the end of the range is incorrect, as there are scenarios
        // in which there could be missing records in the middle. We need a better approach to this that
        // always retrieves all missing records.

        List<DbOperationRecordDto> records = new ArrayList<DbOperationRecordDto>(recordsByKey.size());
        for (Object recordObject : recordsByKey.values()) {
            records.add((DbOperationRecordDto) recordObject);
        }

        return records;
    }

    private List<String> getNewOperationRecordKeys(long beginId, long endId) {
        List<String> keys = new ArrayList<String>((int) (endId - beginId + 1));
        for (long i = beginId; i <= endId; ++i) {
            keys.add(MemcacheKey.DB_OPERATION_RECORD_PREFIX.getName() + i);
        }
        return keys;
    }
}
