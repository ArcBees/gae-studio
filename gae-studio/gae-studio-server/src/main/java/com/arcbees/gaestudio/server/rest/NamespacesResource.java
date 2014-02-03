/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.NamespacesService;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.inject.Inject;

@Path(EndPoints.NAMESPACES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class NamespacesResource {
    private final NamespacesService namespacesService;

    @Inject
    NamespacesResource(NamespacesService namespacesService) {
        this.namespacesService = namespacesService;
    }

    @GET
    public Response getNamespaces() {
        List<AppIdNamespaceDto> namespaces = namespacesService.getNamespaces();

        return Response.ok(namespaces).build();
    }
}
