package com.arcbees.gaestudio.companion.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloResource {
    HelloResource() {
    }

    @POST
    @Path(TestEndPoints.PUT_OBJECT)
    public Response putObject() {
        return Response.ok("asdf").build();
    }

    @GET
    @Path(TestEndPoints.PUT_OBJECT)
    public Response lol2() {
        return Response.ok("asdf2").build();
    }
}
