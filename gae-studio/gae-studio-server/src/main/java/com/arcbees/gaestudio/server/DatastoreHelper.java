/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server;

import com.arcbees.gaestudio.server.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.server.dto.entity.KeyDto;
import com.arcbees.gaestudio.server.dto.entity.ParentKeyDto;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Projection;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;

public class DatastoreHelper {
    public Entity get(KeyDto keyDto) throws EntityNotFoundException {
        ParentKeyDto parentKeyDto = keyDto.getParentKey();
        AppIdNamespaceDto namespaceDto = keyDto.getAppIdNamespaceDto();

        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(namespaceDto.getNamespace());

        Key key;
        if (parentKeyDto != null) {
            Key parentKey = KeyFactory.createKey(parentKeyDto.getKind(), parentKeyDto.getId());

            key = KeyFactory.createKey(parentKey, keyDto.getKind(), keyDto.getId());
        } else {
            key = KeyFactory.createKey(keyDto.getKind(), keyDto.getId());
        }

        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Entity entity = datastoreService.get(key);

        NamespaceManager.set(defaultNamespace);

        return entity;
    }

    public void delete(Key key, String namespace) {
        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(namespace);

        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        datastoreService.delete(key);

        NamespaceManager.set(defaultNamespace);
    }

    public void deleteOnAllNamespaces(Query query) {
        Iterable<Entity> entities = queryOnAllNamespaces(query);
        final Iterable<Key> keys = FluentIterable.from(entities).transform(new Function<Entity, Key>() {
            @Override
            public Key apply(Entity entity) {
                return entity.getKey();
            }
        });

        String defaultNamespace = NamespaceManager.get();
        Iterable<Entity> namespaces = getAllNamespaces();

        for (Entity namespace : namespaces) {
            NamespaceManager.set(extractNamespace(namespace));
            DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

            datastoreService.delete(keys);
        }

        NamespaceManager.set(defaultNamespace);
    }

    public Iterable<Entity> queryOnAllNamespaces(Query query) {
        return queryOnAllNamespaces(query, FetchOptions.Builder.withDefaults());
    }

    public Iterable<Entity> queryOnAllNamespaces(final Query query, final FetchOptions fetchOptions) {
        String defaultNamespace = NamespaceManager.get();

        Iterable<Entity> namespaces = getAllNamespaces();

        FluentIterable<Entity> entities = FluentIterable.from(namespaces)
                .transformAndConcat(new Function<Entity, Iterable<Entity>>() {
                    @Override
                    public Iterable<Entity> apply(Entity namespace) {
                        NamespaceManager.set(extractNamespace(namespace));
                        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

                        Query namespaceAwareQuery = copyQuery(query);

                        return datastoreService.prepare(namespaceAwareQuery).asIterable(fetchOptions);
                    }
                });

        NamespaceManager.set(defaultNamespace);

        return entities;
    }

    private Iterable<Entity> getAllNamespaces() {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Query namespacesQuery = new Query(Entities.NAMESPACE_METADATA_KIND);

        return datastoreService.prepare(namespacesQuery).asIterable();
    }

    private Query copyQuery(Query query) {
        Query newQuery;

        String kind = query.getKind();
        Key ancestor = query.getAncestor();

        if (!Strings.isNullOrEmpty(kind)) {
            if (ancestor != null) {
                newQuery = new Query(kind, ancestor);
            } else {
                newQuery = new Query(kind);
            }
        } else if (ancestor != null) {
            newQuery = new Query(ancestor);
        } else {
            newQuery = new Query();
        }

        newQuery.setFilter(query.getFilter());
        newQuery.setDistinct(query.getDistinct());
        if (query.isKeysOnly()) {
            newQuery.setKeysOnly();
        }

        for (Projection projection : query.getProjections()) {
            newQuery.addProjection(projection);
        }

        for (Query.SortPredicate sortPredicate : query.getSortPredicates()) {
            newQuery.addSort(sortPredicate.getPropertyName(), sortPredicate.getDirection());
        }

        return newQuery;
    }

    private String extractNamespace(Entity namespace) {
        return Entities.getNamespaceFromNamespaceKey(namespace.getKey());
    }
}
