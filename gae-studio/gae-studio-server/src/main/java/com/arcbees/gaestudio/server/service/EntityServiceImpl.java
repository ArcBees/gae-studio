/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.common.base.Strings;

public class EntityServiceImpl implements EntityService {
    private final DatastoreHelper datastoreHelper;

    @Inject
    EntityServiceImpl(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public Entity getEntity(Long entityId,
                            String namespace,
                            String appId,
                            String kind,
                            String parentId,
                            String parentKind) throws EntityNotFoundException {
        AppEngineHelper.disableApiHooks();

        Entity entity;
        ParentKeyDto parentKeyDto = null;

        if (!Strings.isNullOrEmpty(parentId) && !Strings.isNullOrEmpty(parentKind)) {
            parentKeyDto = new ParentKeyDto(parentKind, Long.valueOf(parentId));
        }

        KeyDto keyDto = new KeyDto(kind, entityId, parentKeyDto, new AppIdNamespaceDto(appId, namespace));

        entity = datastoreHelper.get(keyDto);

        return entity;
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
