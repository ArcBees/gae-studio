/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.google.appengine.api.datastore;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
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

public class PropertiesDeserializer implements JsonDeserializer<Map<?, ?>>, JsonSerializer<Map<?, ?>> {
    @Override
    @SuppressWarnings("unchecked")
    public Map deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Map<String, Object> properties = new HashMap<String, Object>();

        for (Map.Entry<String, JsonElement> jsonProperty : json.getAsJsonObject().entrySet()) {
            String key = jsonProperty.getKey();
            PropertyValue propertyValue = context.deserialize(jsonProperty.getValue(), PropertyValue.class);

            properties.put(key, propertyValue.getValue());
        }

        return new LinkedHashMap(properties);
    }

    @Override
    public JsonElement serialize(Map<?, ?> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject map = new JsonObject();

        for (Entry entry : src.entrySet()) {
            Object value = entry.getValue();
            JsonElement serializedValue = serializeEntryValue(value, context);

            map.add(String.valueOf(entry.getKey()), serializedValue);
        }

        return map;
    }

    private JsonElement serializeEntryValue(Object value, JsonSerializationContext context) {
        JsonElement serializedValue = context.serialize(value);
        PropertyType propertyType = getPropertyType(value);

        if (propertyType != PropertyType.NULL) {
            serializedValue = appendPropertyType(serializedValue, propertyType);
        }

        return serializedValue;
    }

    private PropertyType getPropertyType(Object value) {
        if (value instanceof UnindexedValue) {
            value = ((UnindexedValue) value).getValue();
        }

        return EntityMapper.getPropertyType(value);
    }

    private JsonElement appendPropertyType(JsonElement serializedValue, PropertyType propertyType) {
        JsonObject wrapper;
        if (serializedValue.isJsonObject() && serializedValue.getAsJsonObject().has(VALUE)) {
            wrapper = serializedValue.getAsJsonObject();
        } else {
            wrapper = new JsonObject();
            wrapper.add(VALUE, serializedValue);
        }

        wrapper.addProperty(GAE_PROPERTY_TYPE, propertyType.name());

        return wrapper;
    }
}
