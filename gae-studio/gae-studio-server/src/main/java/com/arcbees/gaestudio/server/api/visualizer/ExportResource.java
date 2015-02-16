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
    ExportResource(
            ExportService exportService) {
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
