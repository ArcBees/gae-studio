package com.arcbees.gaestudio.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

public class KindsServiceImpl implements KindsService {
    private final DatastoreHelper datastoreHelper;

    @Inject
    public KindsServiceImpl(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public List<String> getKinds() {
        AppEngineHelper.disableApiHooks();

        Query query = new Query(Entities.KIND_METADATA_KIND);

        Iterable<Entity> entityIterable = datastoreHelper.queryOnAllNamespaces(query);

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
}
