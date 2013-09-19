package com.arcbees.gaestudio.companion.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloResource {
    @GET
    public Response get() {
        return Response.ok().build();
    }
}
