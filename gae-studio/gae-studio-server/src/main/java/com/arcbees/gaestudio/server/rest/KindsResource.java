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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.KindsService;
import com.arcbees.gaestudio.shared.rest.EndPoints;

@Path(EndPoints.KINDS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class KindsResource {
    private final KindsService kindsService;

    @Inject
    KindsResource(KindsService kindsService) {
        this.kindsService = kindsService;
    }

    @GET
    public Response getKinds() {
        ResponseBuilder responseBuilder;
        List<String> kinds = kindsService.getKinds();

        if (kinds.isEmpty()) {
            responseBuilder = Response.status(Status.NOT_FOUND);
        } else {
            responseBuilder = Response.ok(kinds);
        }

        return responseBuilder.build();
    }
}
