/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

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
