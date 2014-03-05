/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.api.profiler;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.profiler.OperationService;
import com.arcbees.gaestudio.server.service.profiler.RecordService;
import com.arcbees.gaestudio.shared.rest.EndPoints;

@Path(EndPoints.RECORD)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class RecordResource {
    private final RecordService recordService;
    private final OperationService operationService;

    @Inject
    RecordResource(RecordService recordService,
                   OperationService operationService) {
        this.recordService = recordService;
        this.operationService = operationService;
    }

    @POST
    public Long startRecording() {
        recordService.startRecording();

        return getMostRecentId();
    }

    @DELETE
    public Long stopRecording() {
        recordService.stopRecording();

        return getMostRecentId();
    }

    private Long getMostRecentId() {
        Long mostRecentId = operationService.getMostRecentId();

        if (mostRecentId == null) {
            mostRecentId = 0L;
        }

        return mostRecentId;
    }
}
