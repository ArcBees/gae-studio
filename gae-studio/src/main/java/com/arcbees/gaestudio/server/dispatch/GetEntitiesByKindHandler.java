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
import java.util.List;
import java.util.logging.Logger;

import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

// TODO add logging
public class GetEntitiesByKindHandler extends AbstractActionHandler<GetEntitiesByKindAction, GetEntitiesByKindResult> {
    private final Logger logger;

    @Inject
    GetEntitiesByKindHandler(Logger logger) {
        super(GetEntitiesByKindAction.class);

        this.logger = logger;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GetEntitiesByKindResult execute(GetEntitiesByKindAction action, ExecutionContext context)
            throws ActionException {
        DispatchHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        if (action.getOffset() != null) {
            fetchOptions.offset(action.getOffset());
        }
        if (action.getLimit() != null) {
            fetchOptions.limit(action.getLimit());
        }

        Query query = new Query(action.getKind());
        List<com.google.appengine.api.datastore.Entity> results = datastore.prepare(query).asList(fetchOptions);

        ArrayList<EntityDto> entities = new ArrayList<EntityDto>(results.size());
        for (com.google.appengine.api.datastore.Entity dbEntity : results) {
            entities.add(EntityMapper.mapDTO(dbEntity));
        }

        return new GetEntitiesByKindResult(entities);
    }
}
