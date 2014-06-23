/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.api.visualizer;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.visualizer.ExportService;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.appengine.labs.repackaged.org.json.JSONException;

@Path(EndPoints.EXPORT_CSV)
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class ExportCsvResource {
    private final ExportService exportService;

    @Inject
    ExportCsvResource(ExportService exportService) {
        this.exportService = exportService;
    }

    @GET
    public Response exportKind(@QueryParam(UrlParameters.KIND) String kind) throws JSONException {
        String data = exportService.exportKindToCsv(kind);

        return Response.ok(data)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + kind + ".csv\"")
                .build();
    }
}
