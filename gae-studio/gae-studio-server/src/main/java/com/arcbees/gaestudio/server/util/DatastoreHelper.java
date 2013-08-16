/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import java.util.List;

import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
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
import com.google.common.collect.Lists;

import static com.google.appengine.api.datastore.Query.FilterOperator;
import static com.google.appengine.api.datastore.Query.FilterOperator.LESS_THAN;
import static com.google.appengine.api.datastore.Query.FilterPredicate;

public class DatastoreHelper {
    public Entity get(KeyDto keyDto) throws EntityNotFoundException {
        ParentKeyDto parentKeyDto = keyDto.getParentKey();
        AppIdNamespaceDto namespaceDto = keyDto.getAppIdNamespace();

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

        List<Entity> entities = FluentIterable.from(namespaces)
                .transformAndConcat(new Function<Entity, Iterable<Entity>>() {
                    @Override
                    public Iterable<Entity> apply(Entity namespace) {
                        NamespaceManager.set(extractNamespace(namespace));
                        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

                        Query namespaceAwareQuery = copyQuery(query);

                        filterGaeKinds(namespaceAwareQuery);

                        return datastoreService.prepare(namespaceAwareQuery).asIterable(fetchOptions);
                    }
                }).toList();

        NamespaceManager.set(defaultNamespace);

        return entities;
    }

    public void filterGaeKinds(Query query) {
        FilterPredicate filter;
        if (Entities.KIND_METADATA_KIND.equals(query.getKind())) {
            filter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, LESS_THAN, Entities.createKindKey("__"));
        } else {
            filter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, LESS_THAN, KeyFactory.createKey("__", 1l));
        }

        List<Query.Filter> filters = Lists.<Query.Filter>newArrayList(filter);
        if (query.getFilter() != null) {
            filters.add(query.getFilter());
            query.setFilter(Query.CompositeFilterOperator.and(filters));
        } else {
            query.setFilter(filter);
        }
    }

    public Iterable<Entity> getAllNamespaces() {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Query namespacesQuery = new Query(Entities.NAMESPACE_METADATA_KIND);
        namespacesQuery.setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY,
                FilterOperator.NOT_EQUAL, Entities.createNamespaceKey(GaeStudioConstants.GAE_NAMESPACE)));

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
