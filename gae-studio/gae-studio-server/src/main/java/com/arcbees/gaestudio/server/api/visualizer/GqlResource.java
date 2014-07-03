/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.api.visualizer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.visualizer.GqlService;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.appengine.api.datastore.Entity;
import com.google.inject.Inject;

@Path(EndPoints.GQL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class GqlResource {
    private final GqlService gqlService;

    @Inject
    GqlResource(GqlService gqlService) {
        this.gqlService = gqlService;
    }

    @GET
    public Response executeGqlRequest(@QueryParam(UrlParameters.QUERY) String gqlRequest) {
        if (isValidRequest(gqlRequest)) {
            Iterable<Entity> result = gqlService.executeGqlRequest(gqlRequest);

            return Response.ok(result).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private boolean isValidRequest(String gqlRequest) {
        return gqlRequest.trim().startsWith("SELECT");
    }
}
