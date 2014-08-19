/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.visualizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

public class KindsServiceImpl implements KindsService {
    private final DatastoreHelper datastoreHelper;

    @Inject
    KindsServiceImpl(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public List<String> getKinds(String namespace) {
        AppEngineHelper.disableApiHooks();

        Query query = new Query(Entities.KIND_METADATA_KIND);

        Iterable<Entity> entityIterable = datastoreHelper.queryOnNamespace(namespace, query);

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

        return Lists.newArrayList(kinds);
    }

    private Iterable<Entity> getKindsInNamespace(String namespace) {
        String defaultNamespace = NamespaceManager.get();
        try {
            DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

            NamespaceManager.set(namespace);

            Query query = new Query(Entities.KIND_METADATA_KIND);

            return datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
        } finally {
            NamespaceManager.set(defaultNamespace);
        }
    }
}
