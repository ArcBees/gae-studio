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

import java.util.logging.Logger;

import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.shared.dispatch.UpdateEntityAction;
import com.arcbees.gaestudio.shared.dispatch.UpdateEntityResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GsonDatastoreFactory;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class UpdateEntityHandler extends AbstractActionHandler<UpdateEntityAction, UpdateEntityResult> {
    private final Logger logger;

    @Inject
    UpdateEntityHandler(Logger logger) {
        super(UpdateEntityAction.class);

        this.logger = logger;
    }

    @Override
    public UpdateEntityResult execute(UpdateEntityAction action, ExecutionContext context) throws ActionException {
        DispatchHelper.disableApiHooks();
        EntityDto entityDTO = action.getEntityDTO();
        Entity dbEntity;

        try {
            Gson gson = GsonDatastoreFactory.create();
            dbEntity = gson.fromJson(entityDTO.getJson(), Entity.class);

            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            datastore.put(dbEntity);
            logger.info("Entity saved");
        } catch (JsonSyntaxException e) {
            throw new ActionException("Error in json syntax");
        } catch (Exception e) {
            throw new ActionException("Unknown class");
        }

        return new UpdateEntityResult(EntityMapper.mapDTO(dbEntity));
    }
}
