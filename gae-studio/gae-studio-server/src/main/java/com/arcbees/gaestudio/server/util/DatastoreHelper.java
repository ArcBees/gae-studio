/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
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
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import static com.google.appengine.api.datastore.Query.FilterOperator;
import static com.google.appengine.api.datastore.Query.FilterOperator.GREATER_THAN;
import static com.google.appengine.api.datastore.Query.FilterOperator.GREATER_THAN_OR_EQUAL;
import static com.google.appengine.api.datastore.Query.FilterOperator.LESS_THAN;
import static com.google.appengine.api.datastore.Query.FilterOperator.LESS_THAN_OR_EQUAL;
import static com.google.appengine.api.datastore.Query.FilterPredicate;

public class DatastoreHelper {
    private static final Set<FilterOperator> inequalityFilters =
            Sets.newHashSet(GREATER_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN, LESS_THAN_OR_EQUAL);
    private static final String ENTITY_PREFIX = "__";

    public Entity get(KeyDto keyDto) throws EntityNotFoundException {
        KeyDto parentKeyDto = keyDto.getParentKey();
        AppIdNamespaceDto namespaceDto = keyDto.getAppIdNamespace();

        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(namespaceDto.getNamespace());
        try {
            Key key;
            if (idIsNumeric(keyDto)) {
                if (parentKeyDto != null) {
                    Key parentKey = KeyFactory.createKey(parentKeyDto.getKind(), parentKeyDto.getId());

                    key = KeyFactory.createKey(parentKey, keyDto.getKind(), keyDto.getId());
                } else {
                    key = KeyFactory.createKey(keyDto.getKind(), keyDto.getId());
                }
            } else {
                if (parentKeyDto != null) {
                    Key parentKey = KeyFactory.createKey(parentKeyDto.getKind(), parentKeyDto.getName());

                    key = KeyFactory.createKey(parentKey, keyDto.getKind(), keyDto.getName());
                } else {
                    key = KeyFactory.createKey(keyDto.getKind(), keyDto.getName());
                }
            }

            DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

            return datastoreService.get(key);
        } finally {
            NamespaceManager.set(defaultNamespace);
        }
    }

    public Entity get(Key key) throws EntityNotFoundException {
        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(key.getNamespace());
        try {
            DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

            return datastoreService.get(key);
        } finally {
            NamespaceManager.set(defaultNamespace);
        }
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

    public Collection<Entity> queryOnAllNamespaces(
            Query query,
            FetchOptions fetchOptions) {
        Integer fetchLimit = fetchOptions.getLimit();
        String defaultNamespace = NamespaceManager.get();

        Iterable<Entity> namespaces = getAllNamespaces();

        Collection<Entity> entities = Lists.newArrayList();
        for (Entity namespace : namespaces) {
            DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

            NamespaceManager.set(extractNamespace(namespace));

            Query namespaceAwareQuery = copyQuery(query);

            boolean canPreFilterGaeKinds = canPreFilterGaeKinds(query);
            if (canPreFilterGaeKinds) {
                preFilterGaeKinds(namespaceAwareQuery);
            }

            Iterables.addAll(entities, datastoreService.prepare(namespaceAwareQuery).asIterable(fetchOptions));

            if (!canPreFilterGaeKinds) {
                entities = Collections2.filter(entities, new Predicate<Entity>() {
                    @Override
                    public boolean apply(Entity input) {
                        return !input.getKind().startsWith(ENTITY_PREFIX);
                    }
                });
            }

            if (fetchLimit != null) {
                int newLimit = fetchLimit - entities.size();
                if (fetchOptions.getLimit() <= 0) {
                    break;
                } else {
                    fetchOptions = fetchOptions.limit(newLimit);
                }
            }
        }

        NamespaceManager.set(defaultNamespace);

        return entities;
    }

    public Iterable<Entity> queryOnNamespace(String namespace, Query query) {
        return queryOnNamespace(namespace, query, FetchOptions.Builder.withDefaults());
    }

    public Iterable<Entity> queryOnNamespace(String namespace, Query query, FetchOptions fetchOptions) {
        if (namespace == null) {
            return queryOnAllNamespaces(query, fetchOptions);
        } else {
            String oldNamespace = NamespaceManager.get();
            try {
                DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
                NamespaceManager.set(namespace);

                Query namespaceAwareQuery = copyQuery(query);

                return toSerializableIterable(datastoreService.prepare(namespaceAwareQuery).asIterable(fetchOptions));
            } finally {
                NamespaceManager.set(oldNamespace);
            }
        }
    }

    /**
     * Add a filter to remove the GAE specific kinds from the query
     *
     * @param query
     * @throws IllegalArgumentException If the Query already contains an inequality filter
     */
    public void preFilterGaeKinds(Query query) throws IllegalArgumentException {
        if (!canPreFilterGaeKinds(query)) {
            throw new IllegalArgumentException("Cannot pre-filter kinds. Query already contains an inequality filter");
        }

        FilterPredicate filter;
        if (Entities.KIND_METADATA_KIND.equals(query.getKind())) {
            filter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, LESS_THAN,
                    Entities.createKindKey(ENTITY_PREFIX));
        } else {
            filter = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, LESS_THAN,
                    KeyFactory.createKey(ENTITY_PREFIX, 1l));
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
        Key namespaceKey = Entities.createNamespaceKey(GaeStudioConstants.GAE_NAMESPACE);

        FilterPredicate ignoreGaeStudioNamespaceFilter =
                new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.NOT_EQUAL, namespaceKey);

        namespacesQuery.setFilter(ignoreGaeStudioNamespaceFilter);

        return datastoreService.prepare(namespacesQuery).asIterable();
    }

    public boolean canPreFilterGaeKinds(Query query) {
        return canPreFilterGaeKinds(query.getFilter());
    }

    private boolean canPreFilterGaeKinds(Query.Filter filter) {
        if (filter != null) {
            if (filter instanceof FilterPredicate) {
                FilterPredicate filterPredicate = (FilterPredicate) filter;
                return !inequalityFilters.contains(filterPredicate.getOperator());
            } else if (filter instanceof Query.CompositeFilter) {
                for (Query.Filter subFilter : ((Query.CompositeFilter) filter).getSubFilters()) {
                    if (!canPreFilterGaeKinds(subFilter)) {
                        return false;
                    }
                }
            }
        }

        return true;
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

    private Iterable<Entity> toSerializableIterable(Iterable<Entity> entities) {
        return Lists.newArrayList(entities);
    }

    private String extractNamespace(Entity namespace) {
        return Entities.getNamespaceFromNamespaceKey(namespace.getKey());
    }

    private boolean idIsNumeric(KeyDto keyDto) {
        return Strings.isNullOrEmpty(keyDto.getName());
    }
}
