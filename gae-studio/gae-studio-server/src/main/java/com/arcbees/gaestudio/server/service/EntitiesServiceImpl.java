package com.arcbees.gaestudio.server.service;

import java.util.Map;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class EntitiesServiceImpl implements EntitiesService {
    private final DatastoreHelper datastoreHelper;

    @Inject
    public EntitiesServiceImpl(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public Iterable<Entity> getEntities(String kind, Integer offset, Integer limit) {
        AppEngineHelper.disableApiHooks();

        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

        if (offset != null) {
            fetchOptions.offset(offset);
        }
        if (limit != null) {
            fetchOptions.limit(limit);
        }

        Query query = new Query(kind);

        return datastoreHelper.queryOnAllNamespaces(query, fetchOptions);
    }

    @Override
    public Entity createEmptyEntity(String kind) {
        AppEngineHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query(kind);
        FetchOptions fetchOptions = FetchOptions.Builder.withOffset(0).limit(1);
        Entity entity = datastore.prepare(query).asList(fetchOptions).get(0);

        Entity emptyEntity = new Entity(kind);
        emptyEntity = setEmptiedProperties(emptyEntity, entity.getProperties());

        return emptyEntity;
    }

    @Override
    public void deleteEntities(String kind, String namespace, DeleteEntities deleteType) {
        AppEngineHelper.disableApiHooks();

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

    @Override
    public Integer getCount(String kind) {
        AppEngineHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query(kind);
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

        return datastore.prepare(query).countEntities(fetchOptions);
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
            return createEmptyPropertyObject(property);
        } catch (Exception e) {
            return null;
        }
    }

    private Object createEmptyPropertyObject(Map.Entry<String, Object> property)
            throws InstantiationException, IllegalAccessException {
        return property.getValue().getClass().newInstance();
    }

    private void deleteByNamespace(String namespace) {
        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(namespace);

        Iterable<Entity> entities = getAllEntitiesInCurrentNamespace();
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

    private void deleteAll() {
        Iterable<Entity> entities = getAllEntitiesOfAllNamespaces();
        deleteEntities(entities);
    }

    private Iterable<Entity> getAllEntitiesInCurrentNamespace() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query().setKeysOnly();
        datastoreHelper.filterGaeKinds(query);

        return datastore.prepare(query).asIterable();
    }

    private Iterable<Entity> getAllEntitiesOfKind(String kind) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        return datastore.prepare(new Query(kind).setKeysOnly()).asIterable();
    }

    private void deleteEntities(Iterable<Entity> entities) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        for (Entity entity : entities) {
            datastore.delete(entity.getKey());
        }
    }

    private Iterable<Entity> getAllEntitiesOfAllNamespaces() {
        return datastoreHelper.queryOnAllNamespaces(new Query().setKeysOnly());
    }
}
