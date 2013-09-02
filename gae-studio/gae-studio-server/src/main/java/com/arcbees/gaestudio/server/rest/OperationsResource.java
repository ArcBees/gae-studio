/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.recorder.MemcacheKey;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.appengine.api.memcache.MemcacheService;

@Path(EndPoints.OPERATIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class OperationsResource {
    private final Logger logger;
    private final MemcacheService memcacheService;

    @Inject
    OperationsResource(Logger logger,
                       MemcacheService memcacheService) {
        this.logger = logger;
        this.memcacheService = memcacheService;
    }

    @GET
    public Response getOperations(@QueryParam(UrlParameters.ID) Long lastId,
                                                    @QueryParam(UrlParameters.LIMIT) Integer limit) {
        Long mostRecentId = getMostRecentId();
        if (mostRecentId == null) {
            logger.info("Could not find a mostRecentId");
            return Response.noContent().build();
        }

        long beginId = lastId + 1;
        // TODO if there is a big difference between beginId and the most recent id, binary search for the true start
        long endId = limit != null ? Math.min(lastId + limit, getMostRecentId()) : getMostRecentId();

        if (beginId > endId) {
            logger.info("No new records since last poll");
            return Response.noContent().build();
        }

        logger.info("Attempting to retrieve ids " + beginId + "-" + endId);

        Map<String, Object> recordsByKey = memcacheService.getAll(getNewOperationRecordKeys(beginId, endId));
        // TODO trimming missing results only from the end of the range is incorrect, as there are scenarios
        // in which there could be missing records in the middle. We need a better approach to this that
        // always retrieves all missing records.

        // TODO optimize this
        List<DbOperationRecordDto> records = new ArrayList<DbOperationRecordDto>(recordsByKey.size());
        for (Object recordObject : recordsByKey.values()) {
            records.add((DbOperationRecordDto) recordObject);
        }

        // The erasure remove the Generic type information. At runtime, userList is simple a list of objects
        // Using GenericEntity allows to keep the info about Generic and jackson knows it has to add the JsonTypeInfo
        GenericEntity<List<DbOperationRecordDto>> entities = new GenericEntity<List<DbOperationRecordDto>>(records) {};

        return Response.ok(entities).build();
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
