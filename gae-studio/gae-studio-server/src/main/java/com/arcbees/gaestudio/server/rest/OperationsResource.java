/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.List;

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
import com.arcbees.gaestudio.server.service.OperationService;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;

@Path(EndPoints.OPERATIONS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class OperationsResource {
    private final OperationService operationService;

    @Inject
    OperationsResource(OperationService operationService) {
        this.operationService = operationService;
    }

    @GET
    public Response getOperations(@QueryParam(UrlParameters.ID) Long lastId,
                                  @QueryParam(UrlParameters.LIMIT) Integer limit) {
        if (lastId == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        List<DbOperationRecordDto> records = operationService.getOperations(lastId, limit);

        if (records == null || records.isEmpty()) {
            return Response.noContent().build();
        }

        // The erasure remove the Generic type information. At runtime, userList is a simple list of objects
        // Using GenericEntity allows to keep the info about Generic and jackson knows it has to add the JsonTypeInfo
        GenericEntity<List<DbOperationRecordDto>> entities = new GenericEntity<List<DbOperationRecordDto>>(records) {
        };

        return Response.ok(entities).build();
    }
}
