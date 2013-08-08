package com.arcbees.gaestudio.server.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.api.client.util.Lists;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

@Path(EndPoints.NAMESPACES)
public class NamespacesResource extends GoogleAnalyticResource {
    private static final String GET_NAMESPACES = "Get Namespaces";

    @GET
    public List<AppIdNamespaceDto> getNamespaces() {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_NAMESPACES);

        AppEngineHelper.disableApiHooks();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query(Entities.NAMESPACE_METADATA_KIND);
        Iterable<Entity> entities = datastore.prepare(query).asIterable();

        Iterable<AppIdNamespaceDto> namespaces = FluentIterable.from(entities)
                .transform(new Function<Entity, AppIdNamespaceDto>() {
                    @Override
                    public AppIdNamespaceDto apply(Entity input) {
                        return new AppIdNamespaceDto(input.getAppId(),
                                Entities.getNamespaceFromNamespaceKey(input.getKey()));
                    }
                });

        return Lists.newArrayList(namespaces);
    }
}
