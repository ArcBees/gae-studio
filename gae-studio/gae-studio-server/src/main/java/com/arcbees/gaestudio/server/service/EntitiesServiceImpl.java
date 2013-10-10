/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

import java.util.Map;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.server.util.DefaultValueGenerator;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityTranslator;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.storage.onestore.v3.OnestoreEntity.EntityProto;

public class EntitiesServiceImpl implements EntitiesService {
    private final DatastoreHelper datastoreHelper;
    private final DefaultValueGenerator defaultValueGenerator;

    @Inject
    EntitiesServiceImpl(DatastoreHelper datastoreHelper,
                        DefaultValueGenerator defaultValueGenerator) {
        this.datastoreHelper = datastoreHelper;
        this.defaultValueGenerator = defaultValueGenerator;
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

        // TODO: Entities can have multiple model versions. We should either select the latest entity or specify an ID
        // to fetch the template (ie. the selected entity) so we get a
        Query query = new Query(kind);
        FetchOptions fetchOptions = FetchOptions.Builder.withOffset(0).limit(1);
        Entity template = datastore.prepare(query).asList(fetchOptions).get(0);

        return createEmptyEntityFromTemplate(template);
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

    private Entity createEmptyEntityFromTemplate(Entity template) {
        // Copy the entity from a known prototype, keeping the type metadata
        EntityProto templateProto = EntityTranslator.convertToPb(template);
        templateProto.getKey().getPath().getElement(0).clearId();

        Entity emptyEntity = EntityTranslator.createFromPb(templateProto);
        emptyProperties(emptyEntity);

        return emptyEntity;
    }

    private void emptyProperties(Entity entity) {
        Map<String, Object> properties = entity.getProperties();

        for (Map.Entry<String, Object> property : properties.entrySet()) {
            String propertyKey = property.getKey();
            Object value = property.getValue();

            if (value instanceof Key) {
                value = createEmptyKey((Key) value);
            } else {
                value = createEmptyPropertyObject(value);
            }

            if (entity.isUnindexedProperty(propertyKey)) {
                entity.setUnindexedProperty(propertyKey, value);
            } else {
                entity.setProperty(propertyKey, value);
            }
        }
    }

    private Object createEmptyKey(Key key) {
        return KeyFactory.createKey(key.getKind(), " ");
    }

    private Object createEmptyPropertyObject(Object property) {
        return defaultValueGenerator.generate(property);
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
