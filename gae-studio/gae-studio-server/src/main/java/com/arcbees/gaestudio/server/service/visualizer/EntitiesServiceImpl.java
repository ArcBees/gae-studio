/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.server.service.visualizer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreCountProvider;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.server.util.DefaultValueGenerator;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.AsyncDatastoreService;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityTranslator;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.storage.onestore.v3.OnestoreEntity.EntityProto;

public class EntitiesServiceImpl implements EntitiesService {
    private final DatastoreHelper datastoreHelper;
    private final DefaultValueGenerator defaultValueGenerator;
    private final DatastoreCountProvider countProvider;

    @Inject
    EntitiesServiceImpl(
            DatastoreHelper datastoreHelper,
            DefaultValueGenerator defaultValueGenerator,
            DatastoreCountProvider countProvider) {
        this.datastoreHelper = datastoreHelper;
        this.defaultValueGenerator = defaultValueGenerator;
        this.countProvider = countProvider;
    }

    @Override
    public Iterable<Entity> getEntities(String kind, String namespace, Integer offset, Integer limit) {
        AppEngineHelper.disableApiHooks();

        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

        if (offset != null) {
            fetchOptions.offset(offset);
        }
        if (limit != null) {
            fetchOptions.limit(limit);
        }

        Query query = new Query(kind);

        return datastoreHelper.queryOnNamespace(namespace, query, fetchOptions);
    }

    @Override
    public Collection<Entity> getEntities(List<Key> keys) {
        AppEngineHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        return datastore.get(keys).values();
    }

    @Override
    public Entity createEmptyEntity(String kind) {
        AppEngineHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // TODO: Entities can have multiple model versions. We should either select the latest entity or specify an ID
        // to fetch the template (ie. the selected entity) so we get a
        Query query = new Query(kind);
        FetchOptions fetchOptions = FetchOptions.Builder.withOffset(0).limit(1);
        List<Entity> entities = datastore.prepare(query).asList(fetchOptions);

        Entity emptyEntity = null;

        if (!entities.isEmpty()) {
            Entity template = entities.get(0);

            emptyEntity = createEmptyEntityFromTemplate(template);
        }

        return emptyEntity;
    }

    @Override
    public void deleteEntities(String kind, String namespace, DeleteEntities deleteType, String encodedKeys) {
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
            case SET:
                deleteSet(encodedKeys);
                break;
        }
    }

    @Override
    public long getCount(String kind, String namespace) {
        AppEngineHelper.disableApiHooks();

        return countProvider.get(kind, namespace);
    }

    @Override
    public List<Key> put(Iterable<Entity> entities) {
        AppEngineHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        return datastore.put(entities);
    }

    @Override
    public Future<List<Key>> putAsync(Iterable<Entity> entities) {
        AppEngineHelper.disableApiHooks();

        AsyncDatastoreService datastoreService = DatastoreServiceFactory.getAsyncDatastoreService();

        return datastoreService.put(entities);
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

    private void deleteSet(String encodedKeys) {
        Iterable<String> stringKeys = Splitter.on(",").split(encodedKeys);

        List<Key> keys = Lists.newArrayList();
        for (String key : stringKeys) {
            keys.add(KeyFactory.stringToKey(key));
        }

        deleteKeys(keys);
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
        datastoreHelper.preFilterGaeKinds(query);

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

    private void deleteKeys(List<Key> keys) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        datastore.delete(keys);
    }

    private Iterable<Entity> getAllEntitiesOfAllNamespaces() {
        return datastoreHelper.queryOnAllNamespaces(new Query().setKeysOnly());
    }
}
