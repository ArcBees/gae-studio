package com.arcbees.gaestudio.server.service;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.api.client.util.Lists;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

public class NamespacesServiceImpl implements NamespacesService {
    private final DatastoreHelper datastoreHelper;

    @Inject
    public NamespacesServiceImpl(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public List<AppIdNamespaceDto> getNamespaces() {
        AppEngineHelper.disableApiHooks();

        Iterable<Entity> entities = datastoreHelper.getAllNamespaces();

        Iterable<AppIdNamespaceDto> namespaces = FluentIterable.from(entities)
                .transform(new Function<Entity, AppIdNamespaceDto>() {
                    @Override
                    public AppIdNamespaceDto apply(Entity input) {
                        return new AppIdNamespaceDto(input.getAppId(),
                                Entities.getNamespaceFromNamespaceKey(input.getKey()));
                    }
                });

        return Lists.newArrayList(namespaces);
    }
}
