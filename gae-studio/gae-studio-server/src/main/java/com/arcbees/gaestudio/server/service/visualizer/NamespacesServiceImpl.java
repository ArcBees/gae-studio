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
    public NamespacesServiceImpl(
            DatastoreHelper datastoreHelper) {
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
