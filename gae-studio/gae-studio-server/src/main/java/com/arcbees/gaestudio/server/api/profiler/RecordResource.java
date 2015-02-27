/**
 * Copyright 2015 ArcBees Inc.
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
    RecordResource(
            RecordService recordService,
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
