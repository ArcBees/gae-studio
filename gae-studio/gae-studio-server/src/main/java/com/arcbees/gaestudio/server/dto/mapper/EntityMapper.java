/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dto.mapper;

import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GsonDatastoreFactory;
import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;

public class EntityMapper {
    @SuppressWarnings("unused")
    private EntityMapper() {
    }

    public static EntityDto mapEntityToDto(Entity dbEntity) {
        Gson gson = GsonDatastoreFactory.create();
        return new EntityDto(mapKey(dbEntity.getKey()), gson.toJson(dbEntity));
    }

    public static Entity mapDtoToEntity(EntityDto entityDto) {
        Gson gson = GsonDatastoreFactory.create();

        return gson.fromJson(entityDto.getJson(), Entity.class);
    }

    private static KeyDto mapKey(Key dbKey) {
        return new KeyDto(dbKey.getKind(), dbKey.getId(), mapParentKey(dbKey.getParent()), mapNamespace(dbKey));
    }

    private static ParentKeyDto mapParentKey(Key dbParentKey) {
        if (dbParentKey == null) {
            return null;
        }
        return new ParentKeyDto(dbParentKey.getKind(), dbParentKey.getId());
    }

    private static AppIdNamespaceDto mapNamespace(Key dbNamespaceKey) {
        if (dbNamespaceKey == null) {
            return null;
        }
        return new AppIdNamespaceDto(dbNamespaceKey.getAppId(), dbNamespaceKey.getNamespace());
    }
}
