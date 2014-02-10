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

import com.google.api.client.util.Lists;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ImportServiceImpl implements ImportService {
    private final EntitiesService entitiesService;

    @Inject
    ImportServiceImpl(EntitiesService entitiesService) {
        this.entitiesService = entitiesService;
    }

    @Override
    public void importData(List<Entity> entities) {
        String defaultNamespace = NamespaceManager.get();

        List<Entity> newEntites = Lists.newArrayList();

        for (Entity entity : entities) {
            Entity newEntity = createEntityCopy(entity);
            newEntites.add(newEntity);
        }

        entitiesService.put(newEntites);

        NamespaceManager.set(defaultNamespace);
    }

    private Entity createEntityCopy(Entity entity) {
        Key newKey;

        Key key = entity.getKey();
        NamespaceManager.set(key.getNamespace());

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
