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

import com.google.api.client.util.Lists;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ImportServiceImpl implements ImportService {
    private final EntitiesService entitiesService;

    @Inject
    ImportServiceImpl(
            EntitiesService entitiesService) {
        this.entitiesService = entitiesService;
    }

    @Override
    public void importEntities(List<Entity> entities) {
        String defaultNamespace = NamespaceManager.get();
        List<Entity> copies = Lists.newArrayList();

        for (Entity entity : entities) {
            // Entities imported might not have the correct appId, we need to make a copy
            copies.add(createEntityCopy(entity));
        }

        entitiesService.putAsync(copies);

        NamespaceManager.set(defaultNamespace);
    }

    private Entity createEntityCopy(Entity entity) {
        Key key = entity.getKey();
        NamespaceManager.set(key.getNamespace());

        Key newKey;
        if (key.getId() == 0) {
            newKey = KeyFactory.createKey(key.getKind(), key.getName());
        } else {
            newKey = KeyFactory.createKey(key.getKind(), key.getId());
        }

        Entity newEntity = new Entity(newKey);
        newEntity.setPropertiesFrom(entity);

        return newEntity;
    }
}
