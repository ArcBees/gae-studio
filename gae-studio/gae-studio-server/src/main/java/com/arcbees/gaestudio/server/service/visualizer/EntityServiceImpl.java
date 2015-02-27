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

import javax.inject.Inject;

import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class EntityServiceImpl implements EntityService {
    private final DatastoreHelper datastoreHelper;

    @Inject
    EntityServiceImpl(
            DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public Entity getEntity(String encodedKey) throws EntityNotFoundException {
        AppEngineHelper.disableApiHooks();

        Key key = KeyFactory.stringToKey(encodedKey);

        return datastoreHelper.get(key);
    }

    @Override
    public Entity updateEntity(Entity entity) throws EntityNotFoundException {
        AppEngineHelper.disableApiHooks();

        entity.getKey();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(entity);

        // This is required because put does not update the prototype in the reference
        return datastore.get(entity.getKey());
    }

    @Override
    public void deleteEntity(Key entityKey) {
        datastoreHelper.delete(entityKey, entityKey.getNamespace());
    }
}
