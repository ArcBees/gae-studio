/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dto.mapper;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Category;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.IMHandle;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;
import com.google.appengine.api.datastore.Rating;
import com.google.appengine.api.datastore.ShortBlob;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class EntityMapper {
    private final Gson gson;

    @Inject
    EntityMapper(Gson gson) {
        this.gson = gson;
    }

    public static PropertyType getPropertyType(Object property) {
        PropertyType type = PropertyType.NULL;

        if (property instanceof String) {
            type = PropertyType.STRING;
        } else if (property instanceof Text) {
            type = PropertyType.TEXT;
        } else if (property instanceof Date) {
            type = PropertyType.DATE;
        } else if (property instanceof Rating) {
            type = PropertyType.RATING;
        } else if (property instanceof Long) {
            type = PropertyType.NUMERIC;
        } else if (property instanceof Double) {
            type = PropertyType.FLOATING;
        } else if (property instanceof Boolean) {
            type = PropertyType.BOOLEAN;
        } else if (property instanceof PhoneNumber) {
            type = PropertyType.PHONE_NUMBER;
        } else if (property instanceof Email) {
            type = PropertyType.EMAIL;
        } else if (property instanceof Link) {
            type = PropertyType.LINK;
        } else if (property instanceof Category) {
            type = PropertyType.CATEGORY;
        } else if (property instanceof PostalAddress) {
            type = PropertyType.POSTAL_ADDRESS;
        } else if (property instanceof GeoPt) {
            type = PropertyType.GEO_PT;
        } else if (property instanceof EmbeddedEntity) {
            type = PropertyType.EMBEDDED;
        } else if (property instanceof Collection) {
            type = PropertyType.COLLECTION;
        } else if (property instanceof Key) {
            type = PropertyType.KEY;
        } else if (property instanceof BlobKey) {
            type = PropertyType.BLOB_KEY;
        } else if (property instanceof IMHandle) {
            type = PropertyType.IM_HANDLE;
        } else if (property instanceof User) {
            type = PropertyType.USER;
        } else if (property instanceof Blob) {
            type = PropertyType.BLOB;
        } else if (property instanceof ShortBlob) {
            type = PropertyType.SHORT_BLOB;
        }

        return type;
    }

    public List<EntityDto> mapEntitiesToDtos(Iterable<Entity> entities) {
        List<EntityDto> entitiesDtos = Lists.newArrayList();

        for (Entity entity : entities) {
            entitiesDtos.add(mapEntityToDto(entity));
        }

        return entitiesDtos;
    }

    public EntityDto mapEntityToDto(Entity dbEntity) {
        String json = gson.toJson(dbEntity);

        return new EntityDto(mapKeyToKeyDto(dbEntity.getKey()), json);
    }

    public Entity mapDtoToEntity(EntityDto entityDto) {
        return gson.fromJson(entityDto.getJson(), Entity.class);
    }

    public KeyDto mapKeyToKeyDto(Key dbKey) {
        KeyDto parentKey = null;
        if (dbKey.getParent() != null) {
            parentKey = mapKeyToKeyDto(dbKey.getParent());
        }
        return new KeyDto(getEncodedKey(dbKey), dbKey.getKind(), dbKey.getId(), dbKey.getName(),
                parentKey, mapNamespace(dbKey));
    }

    private String getEncodedKey(Key key) {
        if (key.isComplete()) {
            return KeyFactory.keyToString(key);
        } else {
            return null;
        }
    }

    private AppIdNamespaceDto mapNamespace(Key dbNamespaceKey) {
        if (dbNamespaceKey == null) {
            return null;
        }
        return new AppIdNamespaceDto(dbNamespaceKey.getAppId(), dbNamespaceKey.getNamespace());
    }
}
