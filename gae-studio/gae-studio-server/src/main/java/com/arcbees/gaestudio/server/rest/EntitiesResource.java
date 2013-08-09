/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.server.guice.GaeStudioResource;
import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.arcbees.gaestudio.shared.rest.UrlParameters;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

@GaeStudioResource
@Path(EndPoints.ENTITIES)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntitiesResource extends GoogleAnalyticResource {
    private static final String GET_ENTITIES_BY_KIND = "Get Entities By Kind";
    private static final String GET_ENTITY_COUNT_BY_KIND = "Get Entity Count By Kind";
    private static final String GET_EMPTY_KIND_ENTITY = "Get Empty Kind Entity";
    private static final String DELETE_ENTITIES = "Delete Entities by ";

    private final DatastoreHelper datastoreHelper;
    private final SubresourceFactory subresourceFactory;

    @Inject
    EntitiesResource(DatastoreHelper datastoreHelper,
                     SubresourceFactory subresourceFactory) {
        this.datastoreHelper = datastoreHelper;
        this.subresourceFactory = subresourceFactory;
    }

    @GET
    public List<EntityDto> getEntities(@QueryParam(UrlParameters.KIND) String kind,
                                       @QueryParam(UrlParameters.OFFSET) Integer offset,
                                       @QueryParam(UrlParameters.LIMIT) Integer limit) {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_ENTITIES_BY_KIND);

        AppEngineHelper.disableApiHooks();

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

        AppEngineHelper.disableApiHooks();
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

    @DELETE
    public void deleteEntities(@QueryParam(UrlParameters.KIND) String kind,
                               @QueryParam(UrlParameters.NAMESPACE) String namespace,
                               @QueryParam(UrlParameters.TYPE) DeleteEntities deleteType) {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, getEvent(deleteType));

        AppEngineHelper.disableApiHooks();

        deleteEntities(deleteType, kind, namespace);
    }

    @GET
    @Path(EndPoints.COUNT)
    public Integer getCount(@QueryParam(UrlParameters.KIND) String kind) {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_ENTITY_COUNT_BY_KIND);

        AppEngineHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query(kind);
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        Integer count = datastore.prepare(query).countEntities(fetchOptions);

        return count;
    }

    @Path(EndPoints.ID)
    public EntityResource getEntityResource(@PathParam(UrlParameters.ID) Long id) {
        return subresourceFactory.createEntityResource(id);
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

    private void deleteEntities(DeleteEntities deleteType,
                                String kind,
                                String namespace) {
        switch (deleteType) {
            case KIND:
                deleteByKind(kind);
                break;
            case NAMESPACE:
                deleteByNamespace(namespace);
                break;
            case KIND_NAMESPACE:
                deleteByKindAndNamespace(kind, namespace);
                break;
            case ALL:
                deleteAll();
                break;
        }
    }

    private void deleteByNamespace(String namespace) {
        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(namespace);

        Iterable<Entity> entities = getAllEntities();
        deleteEntities(entities);

        NamespaceManager.set(defaultNamespace);
    }

    private void deleteByKindAndNamespace(String kind, String namespace) {
        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(namespace);

        Iterable<Entity> entities = getAllEntitiesOfKind(kind);
        deleteEntities(entities);

        NamespaceManager.set(defaultNamespace);
    }

    private void deleteByKind(String kind) {
        Query query = new Query(kind).setKeysOnly();
        datastoreHelper.deleteOnAllNamespaces(query);
    }

    private void deleteEntities(Iterable<Entity> entities) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        for (Entity entity : entities) {
            datastore.delete(entity.getKey());
        }
    }

    private void deleteAll() {
        Iterable<Entity> entities = getAllEntitiesOfAllNamespaces();
        deleteEntities(entities);
    }

    private Iterable<Entity> getAllEntities() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        return datastore.prepare(new Query().setKeysOnly()).asIterable();
    }

    private Iterable<Entity> getAllEntitiesOfKind(String kind) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        return datastore.prepare(new Query(kind).setKeysOnly()).asIterable();
    }

    private Iterable<Entity> getAllEntitiesOfAllNamespaces() {
        return datastoreHelper.queryOnAllNamespaces(new Query().setKeysOnly());
    }

    private String getEvent(DeleteEntities deleteEntities) {
        return DELETE_ENTITIES + deleteEntities.name();
    }
}
