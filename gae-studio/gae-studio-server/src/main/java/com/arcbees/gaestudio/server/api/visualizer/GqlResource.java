package com.arcbees.gaestudio.server.api.visualizer;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.appengine.api.datastore.Entity;

@Path(EndPoints.GQL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class GqlResource {
    @GET
    public Response executeGqlRequest(String request) {
        return Response.ok(new ArrayList<Entity>()).build();
    }
}
