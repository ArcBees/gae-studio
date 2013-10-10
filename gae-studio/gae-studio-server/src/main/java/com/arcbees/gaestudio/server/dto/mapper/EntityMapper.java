/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dto.mapper;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityTranslator;
import com.google.appengine.api.datastore.GsonDatastoreFactory;
import com.google.appengine.api.datastore.Key;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.storage.onestore.v3.OnestoreEntity.EntityProto;
import com.google.storage.onestore.v3.OnestoreEntity.Property;
import com.google.storage.onestore.v3.OnestoreEntity.Property.Meaning;
import com.google.storage.onestore.v3.OnestoreEntity.PropertyValue;

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
        Map<String, PropertyType> propertyTypes = getPropertyTypes(dbEntity);

        String json = gson.toJson(dbEntity);
        json = appendPropertyTypes(json, propertyTypes);

        return new EntityDto(mapKeyToKeyDto(dbEntity.getKey()), json);
    }

    public static Entity mapDtoToEntity(EntityDto entityDto) {
        Gson gson = GsonDatastoreFactory.create();

        return gson.fromJson(entityDto.getJson(), Entity.class);
    }

    public static KeyDto mapKeyToKeyDto(Key dbKey) {
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

    private static Map<String, PropertyType> getPropertyTypes(Entity dbEntity) {
        EntityProto proto = EntityTranslator.convertToPb(dbEntity);
        Map<String, PropertyType> metadata = Maps.newHashMap();

        List<Property> properties = Lists.newArrayList(proto.propertys());
        properties.addAll(proto.rawPropertys());

        for (Property property : properties) {
            metadata.put(property.getName(), getPropertyType(property));
        }

        return metadata;
    }

    private static PropertyType getPropertyType(Property property) {
        PropertyType type = PropertyType.NULL;

        // TODO: PostalAddress, PhoneNumber, Email, Link, Category, Rating, IMHandle, GeoPt, User, Blob,
        // ShortBlob, BlobKey, EmbeddedEntity

        if (property.hasValue()) {
            PropertyValue value = property.getValue();
            Meaning meaning = property.hasMeaning() ? property.getMeaningEnum() : Meaning.NO_MEANING;

            if (value.hasInt64Value()) {
                if (meaning == Meaning.GD_WHEN) {
                    type = PropertyType.DATE;
                } else {
                    type = PropertyType.NUMERIC;
                }
            } else if (value.hasBooleanValue()) {
                type = PropertyType.BOOLEAN;
            } else if (value.hasDoubleValue()) {
                type = PropertyType.FLOATING;
            } else if (value.hasStringValue()) {
                type = PropertyType.STRING;
            }
        }

        return type;
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
                if (value.isJsonObject()) {
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
