/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.google.appengine.api.datastore;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.Set;

import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.server.util.JsonUtil;
import com.arcbees.gaestudio.shared.PropertyType;
import com.google.appengine.api.datastore.Entity.UnindexedValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import static com.arcbees.gaestudio.shared.PropertyName.GAE_PROPERTY_TYPE;
import static com.arcbees.gaestudio.shared.PropertyName.VALUE;
import static com.arcbees.gaestudio.shared.PropertyType.COLLECTION;
import static com.arcbees.gaestudio.shared.PropertyType.NULL;

public class PropertyValueAdapter implements JsonDeserializer<PropertyValue>, JsonSerializer<PropertyValue> {
    private static final Set<PropertyType> EXCLUDED_METADATA_PROPERTY_TYPES = EnumSet.noneOf(PropertyType.class);

    static {
        EXCLUDED_METADATA_PROPERTY_TYPES.add(NULL);
        EXCLUDED_METADATA_PROPERTY_TYPES.add(COLLECTION);
    }

    @Override
    public JsonElement serialize(PropertyValue src, Type typeOfSrc, JsonSerializationContext context) {
        Object value = src.getValue();
        JsonElement serializedValue;

        if (UnindexedValueAdapter.isUnindexedValue(value)) {
            serializedValue = context.serialize(value, UnindexedValue.class);
        } else {
            PropertyType propertyType = getPropertyType(value);
            Class<?> mappedClass = getMappedClass(propertyType);

            serializedValue = context.serialize(value, mappedClass);

            if (!EXCLUDED_METADATA_PROPERTY_TYPES.contains(propertyType)) {
                serializedValue = appendPropertyType(serializedValue, propertyType.getRepresentation());
            }
        }

        return serializedValue;
    }

    @Override
    public PropertyValue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Object value;

        if (UnindexedValueAdapter.isUnindexedValue(json)) {
            value = context.deserialize(json, UnindexedValue.class);
        } else {
            PropertyType propertyType = extractPropertyType(json);
            Class<?> mappedClass = getMappedClass(propertyType);

            if (JsonUtil.hasEmbedValue(json)) {
                json = json.getAsJsonObject().get(VALUE);
            }

            value = context.deserialize(json, mappedClass);
            value = cleanupValue(value, propertyType);
        }

        return new PropertyValue(value);
    }

    private PropertyType getPropertyType(Object value) {
        if (value instanceof UnindexedValue) {
            value = ((UnindexedValue) value).getValue();
        }

        return EntityMapper.getPropertyType(value);
    }

    private JsonElement appendPropertyType(JsonElement serializedValue, PropertyType propertyType) {
        JsonObject wrapper;
        if (JsonUtil.hasEmbedValue(serializedValue)) {
            wrapper = serializedValue.getAsJsonObject();
        } else {
            wrapper = new JsonObject();
            wrapper.add(VALUE, serializedValue);
        }

        wrapper.addProperty(GAE_PROPERTY_TYPE, propertyType.name());

        return wrapper;
    }

    private Object cleanupValue(Object value, PropertyType propertyType) {
        if (propertyType == PropertyType.STRING && value instanceof String) {
            String string = (String) value;
            if (string.length() > DataTypeUtils.MAX_STRING_PROPERTY_LENGTH) {
                value = new Text(string);
            }
        }

        return value;
    }

    private PropertyType extractPropertyType(JsonElement jsonElement) {
        PropertyType propertyType = PropertyType.NULL;

        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has(GAE_PROPERTY_TYPE)) {
                String propertyTypeName = jsonObject.get(GAE_PROPERTY_TYPE).getAsString();
                propertyType = PropertyType.valueOf(propertyTypeName);
            }
        } else if (jsonElement.isJsonArray()) {
            propertyType = COLLECTION;
        }

        return propertyType;
    }

    private Class<?> getMappedClass(PropertyType propertyType) {
        Class<?> mappedClass;
        try {
            mappedClass = Class.forName(propertyType.getMappedClass());
        } catch (ClassNotFoundException e) {
            mappedClass = Object.class;
        }
        return mappedClass;
    }
}
