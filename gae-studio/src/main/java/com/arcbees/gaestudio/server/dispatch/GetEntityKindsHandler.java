/**
 * Copyright 2013 ArcBees Inc.
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

package com.arcbees.gaestudio.server.dispatch;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetEntityKindsHandler
        extends AbstractActionHandler<GetEntityKindsAction, GetEntityKindsResult> {

    @Inject
    public GetEntityKindsHandler() {
        super(GetEntityKindsAction.class);
    }

    @Override
    public GetEntityKindsResult execute(GetEntityKindsAction action, ExecutionContext context)
            throws ActionException {

        DispatchHelper.disableApiHooks();

        Query query = new Query(Entities.KIND_METADATA_KIND);
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

        Iterable<Entity> entityIterable = datastoreService.prepare(query).asIterable();

        Iterable<String> iterator = Iterables.transform(entityIterable,
                new Function<Entity, String>() {
            @Override
            public String apply(@Nullable Entity entity) {
                String kindName;

                if (entity != null) {
                    kindName =  entity.getKey().getName();
                } else {
                    kindName = "";
                }

                return kindName;
            }
        });

        ArrayList<String> kinds = Lists.newArrayList(iterator);


        return new GetEntityKindsResult(kinds);
    }
}
