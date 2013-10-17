/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dto.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.google.appengine.api.datastore.Category;
import com.google.appengine.api.datastore.Email;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.GsonDatastoreFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.PhoneNumber;
import com.google.appengine.api.datastore.PostalAddress;
import com.google.appengine.api.datastore.Rating;
import com.google.appengine.api.datastore.Text;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import static com.arcbees.gaestudio.shared.PropertyName.GAE_PROPERTY_TYPE;
import static com.arcbees.gaestudio.shared.PropertyName.PROPERTY_MAP;
import static com.arcbees.gaestudio.shared.PropertyName.VALUE;

public class EntityMapper {
    @SuppressWarnings("unused")
    private EntityMapper() {
    }

    public static List<EntityDto> mapEntitiesToDtos(Iterable<Entity> entities) {
        List<EntityDto> entitiesDtos = Lists.newArrayList();

        for (Entity entity : entities) {
            entitiesDtos.add(EntityMapper.mapEntityToDto(entity));
        }

        return entitiesDtos;
    }

    public static EntityDto mapEntityToDto(Entity dbEntity) {
        Gson gson = GsonDatastoreFactory.create();
        String json = gson.toJson(dbEntity);

        return new EntityDto(mapKeyToKeyDto(dbEntity.getKey()), json);
    }

    public static Entity mapDtoToEntity(EntityDto entityDto) {
        Gson gson = GsonDatastoreFactory.create();

        return gson.fromJson(entityDto.getJson(), Entity.class);
    }

    public static KeyDto mapKeyToKeyDto(Key dbKey) {
        return new KeyDto(dbKey.getKind(), dbKey.getId(), mapParentKey(dbKey.getParent()), mapNamespace(dbKey));
    }

    public static PropertyType getPropertyType(Object property) {
        PropertyType type = PropertyType.NULL;

        // TODO: IMHandle, User, Blob, ShortBlob, BlobKey, EmbeddedEntity

        if (property instanceof String || property instanceof Text) {
            type = PropertyType.STRING;
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
        }

        return type;
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

    private static String appendPropertyTypes(String json, Map<String, PropertyType> propertyTypes) {
        JsonParser jsonParser = new JsonParser();
        JsonObject entity = jsonParser.parse(json).getAsJsonObject();
        JsonObject properties = entity.getAsJsonObject(PROPERTY_MAP);

        for (Entry<String, PropertyType> entry : propertyTypes.entrySet()) {
            String propertyKey = entry.getKey();

            if (properties.has(propertyKey)) {
                JsonElement value = properties.get(propertyKey);

                JsonObject wrapper;
                if (value.isJsonObject() && value.getAsJsonObject().has(VALUE)) {
                    wrapper = value.getAsJsonObject();
                } else {
                    wrapper = new JsonObject();
                    wrapper.add(VALUE, value);
                }

                wrapper.add(GAE_PROPERTY_TYPE, new JsonPrimitive(entry.getValue().name()));

                properties.remove(propertyKey);
                properties.add(propertyKey, wrapper);
            }
        }

        return new Gson().toJson(entity);
    }
}
