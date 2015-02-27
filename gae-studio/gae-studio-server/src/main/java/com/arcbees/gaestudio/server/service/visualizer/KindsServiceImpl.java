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
    KindsServiceImpl(
            DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public List<String> getKinds(String namespace) {
        AppEngineHelper.disableApiHooks();

        Iterable<Entity> entityIterable = datastoreHelper
                .queryOnNamespace(namespace, new Query(Entities.KIND_METADATA_KIND));

        return getKinds(entityIterable);
    }

    private ArrayList<String> getKinds(Iterable<Entity> entityIterable) {
        Set<String> kinds = FluentIterable.from(entityIterable)
                .transform(new Function<Entity, String>() {
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
