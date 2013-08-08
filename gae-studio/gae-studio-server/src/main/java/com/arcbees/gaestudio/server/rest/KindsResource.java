package com.arcbees.gaestudio.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

@GaeStudioResource
@Path(EndPoints.KINDS)
public class KindsResource extends GoogleAnalyticResource {
    private static final String GET_ENTITY_KINDS = "Get Entity Kinds";

    private final DatastoreHelper datastoreHelper;

    @Inject
    KindsResource(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @GET
    public List<String> getKinds() {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_ENTITY_KINDS);

        AppEngineHelper.disableApiHooks();

        Query query = new Query(Entities.KIND_METADATA_KIND);
        Iterable<Entity> entityIterable = datastoreHelper.queryOnAllNamespaces(query);

        return getKinds(entityIterable);
    }

    private ArrayList<String> getKinds(Iterable<Entity> entityIterable) {
        Set<String> kinds = FluentIterable.from(entityIterable).transform(new Function<Entity, String>() {
            @Override
            public String apply(Entity entity) {
                String kindName;

                if (entity != null) {
                    kindName = entity.getKey().getName();
                } else {
                    kindName = "";
                }

                return kindName;
            }
        }).toSet();

        return com.google.common.collect.Lists.newArrayList(kinds);
    }
}
