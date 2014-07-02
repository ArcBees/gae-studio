package com.arcbees.gaestudio.server.api.visualizer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.server.util.GqlQuery;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.inject.Inject;

@Path(EndPoints.GQL)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@GaeStudioResource
public class GqlResource {
    private final DatastoreHelper datastoreHelper;

    @Inject
    GqlResource(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @GET
    public Response executeGqlRequest(@QueryParam(UrlParameters.QUERY) String gqlRequest) {

        if (isValidRequest(gqlRequest)) {
            AppEngineHelper.disableApiHooks();

            FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

            GqlQuery gql = new GqlQuery(gqlRequest);

            Iterable<Entity> result = datastoreHelper.queryOnAllNamespaces(gql.query(), fetchOptions);

            return Response.ok(result).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private boolean isValidRequest(String gqlRequest) {
        return gqlRequest.trim().startsWith("SELECT");
    }
}
