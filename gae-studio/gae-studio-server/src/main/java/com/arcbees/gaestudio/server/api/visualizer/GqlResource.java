package com.arcbees.gaestudio.server.api.visualizer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.inject.Inject;

@Path(EndPoints.GQL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class GqlResource {
    private final DatastoreHelper datastoreHelper;

    @Inject
    public GqlResource(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @GET
    public Response executeGqlRequest(String gqlRequest) {
        AppEngineHelper.disableApiHooks();

        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

        Query query = new Query(gqlRequest);

        Iterable<Entity> result = datastoreHelper.queryOnAllNamespaces(query, fetchOptions);

        return Response.ok(result).build();
    }
}
