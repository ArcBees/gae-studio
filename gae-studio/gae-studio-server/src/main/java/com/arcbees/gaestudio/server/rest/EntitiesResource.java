package com.arcbees.gaestudio.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.arcbees.gaestudio.server.DatastoreHelper;
import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.server.dispatch.DispatchHelper;
import com.arcbees.gaestudio.server.dto.entity.EntityDto;
import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

@Path(EndPoints.ENTITIES)
public class EntitiesResource extends GoogleAnalyticResource {
    private static final String GET_ENTITIES_BY_KIND = "Get Entities By Kind";
    private static final String GET_ENTITY_COUNT_BY_KIND = "Get Entity Count By Kind";
    private static final String GET_EMPTY_KIND_ENTITY = "Get Empty Kind Entity";

    private final DatastoreHelper datastoreHelper;

    @Inject
    EntitiesResource(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @GET
    public List<EntityDto> getEntities(@QueryParam(UrlParameters.KIND) String kind,
                                       @QueryParam(UrlParameters.OFFSET) Integer offset,
                                       @QueryParam(UrlParameters.LIMIT) Integer limit) {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_ENTITIES_BY_KIND);

        DispatchHelper.disableApiHooks();

        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        if (offset != null) {
            fetchOptions.offset(offset);
        }
        if (limit != null) {
            fetchOptions.limit(limit);
        }

        Query query = new Query(kind);
        Iterable<Entity> results = datastoreHelper.queryOnAllNamespaces(query, fetchOptions);

        List<EntityDto> entities = new ArrayList<EntityDto>();
        for (Entity dbEntity : results) {
            entities.add(EntityMapper.mapDTO(dbEntity));
        }

        return entities;
    }

    @POST
    public EntityDto createEntity(String kind) {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_EMPTY_KIND_ENTITY);

        DispatchHelper.disableApiHooks();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity emptyEntity = new Entity(kind);
        try {
            Query query = new Query(kind);
            FetchOptions fetchOptions = FetchOptions.Builder.withOffset(0).limit(1);
            Entity entity = datastore.prepare(query).asList(fetchOptions).get(0);
            emptyEntity = setEmptiedProperties(emptyEntity, entity.getProperties());
        } catch (Exception e) {
            // TODO: Be able to generate entity base schema from the pojo that haven't been save yet tot the datastore
            // We will need to create an implementation to support Objectify, Twig persist, etc.
            // For objectify we can use : ObjectifyService.factory().getMetadataForEntity(String kind);
            // And call method metadata.toEntity
        }

        return EntityMapper.mapDTO(emptyEntity);
    }

    @GET
    @Path(EndPoints.COUNT)
    public Integer getCount(@QueryParam(UrlParameters.KIND) String kind) {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_ENTITY_COUNT_BY_KIND);

        DispatchHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query(kind);
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        Integer count = datastore.prepare(query).countEntities(fetchOptions);

        return count;
    }

    private Entity setEmptiedProperties(Entity entity,
                                        Map<String, Object> properties) {
        for (Map.Entry<String, Object> property : properties.entrySet()) {
            Object value = property.getValue();

            if (value instanceof Key) {
                value = createEmptyKey((Key) value);
            } else {
                value = createEmptyArbitraryObject(property);
            }
            entity.setProperty(property.getKey(), value);
        }

        return entity;
    }

    private Object createEmptyKey(Key key) {
        return KeyFactory.createKey(key.getKind(), " ");
    }

    private Object createEmptyArbitraryObject(Map.Entry<String, Object> property) {
        try {
            // Reset all property objects in the datastore with a no-args constructor
            return property.getValue().getClass().newInstance();
        } catch (Exception e) {
            // Otherwise set null
            return null;
        }
    }
}
