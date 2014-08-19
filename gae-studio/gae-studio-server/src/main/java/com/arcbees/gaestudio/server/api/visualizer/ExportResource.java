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

import org.json.JSONException;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.service.visualizer.ExportService;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.common.base.Strings;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class ExportResource {
    private final ExportService exportService;

    @Inject
    ExportResource(ExportService exportService) {
        this.exportService = exportService;
    }

    @GET
    @Path(EndPoints.EXPORT_JSON)
    public Response exportAsJson(@QueryParam(UrlParameters.KIND) String kind,
                                 @QueryParam(UrlParameters.NAMESPACE) String namespace,
                                 @QueryParam(UrlParameters.KEY) String encodedKeys) {
        String data = exportService.exportToJson(kind, namespace, encodedKeys);

        return Response.ok(data)
                .header(HttpHeaders.CONTENT_DISPOSITION, getContentDisposition(kind, namespace, "json"))
                .build();
    }

    @GET
    @Path(EndPoints.EXPORT_CSV)
    public Response exportAsCsv(@QueryParam(UrlParameters.KIND) String kind,
                                @QueryParam(UrlParameters.NAMESPACE) String namespace,
                                @QueryParam(UrlParameters.KEY) String encodedKeys) throws JSONException {
        String data = exportService.exportToCsv(kind, namespace, encodedKeys);

        return Response.ok(data)
                .header(HttpHeaders.CONTENT_DISPOSITION, getContentDisposition(kind, namespace, "csv"))
                .build();
    }

    private String getContentDisposition(String kind, String namespace, String extension) {
        String filename = getFilename(kind, namespace);

        return "attachment; filename=\"" + filename + "." + extension + "\"";
    }

    private String getFilename(String kind, String namespace) {
        if (Strings.isNullOrEmpty(kind)) {
            return "SelectedSubset";
        } else {
            if (Strings.isNullOrEmpty(namespace)) {
                return kind;
            } else {
                return kind + "_" + namespace;
            }
        }
    }
}
