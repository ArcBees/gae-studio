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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.appengine.api.datastore.Entity.UnindexedValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import static com.arcbees.gaestudio.shared.PropertyName.GAE_PROPERTY_TYPE;

public class PropertiesDeserializer implements JsonDeserializer<Map> {
    public static Object deserializeElement(JsonElement jsonValue, JsonDeserializationContext context) {
        PropertyType propertyType = extractPropertyType(jsonValue);
        Object value;

        if (UnindexedValueAdapter.isUnindexedValue(jsonValue) || propertyType != PropertyType.NULL) {
            jsonValue = jsonValue.getAsJsonObject().get(UnindexedValueAdapter.VALUE);
        }

        switch (propertyType) {
            case NUMERIC:
                value = context.deserialize(jsonValue, Long.class);
                break;
            case FLOATING:
                value = context.deserialize(jsonValue, Double.class);
                break;
            case STRING:
                value = context.deserialize(jsonValue, String.class);
                break;
            case BOOLEAN:
                value = context.deserialize(jsonValue, Boolean.class);
                break;
            case DATE:
                value = context.deserialize(jsonValue, Date.class);
                break;
            default:
                value = context.deserialize(jsonValue, Object.class);
        }

        return value;
    }

    public static PropertyType extractPropertyType(JsonElement jsonElement) {
        PropertyType propertyType = PropertyType.NULL;

        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has(GAE_PROPERTY_TYPE.getPropertyName())) {
                String propertyTypeName = jsonObject.get(GAE_PROPERTY_TYPE.getPropertyName()).getAsString();
                propertyType = PropertyType.valueOf(propertyTypeName);
            }
        }

        return propertyType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Map<String, Object> properties = new HashMap<String, Object>();

        for (Map.Entry<String, JsonElement> jsonProperty : json.getAsJsonObject().entrySet()) {
            String key = jsonProperty.getKey();
            Object value = deserializeProperty(jsonProperty.getValue(), context);

            properties.put(key, value);
        }

        return new LinkedHashMap(properties);
    }

    private Object deserializeProperty(JsonElement jsonValue, JsonDeserializationContext context) {
        Object value;
        if (isKey(jsonValue)) {
            value = context.deserialize(jsonValue, Key.class);
        } else if (UnindexedValueAdapter.isUnindexedValue(jsonValue)) {
            value = context.deserialize(jsonValue, UnindexedValue.class);
        } else {
            value = deserializeElement(jsonValue, context);
        }

        return value;
    }

    private boolean isKey(JsonElement jsonValueElement) {
        if (jsonValueElement.isJsonObject()) {
            JsonObject valueObject = jsonValueElement.getAsJsonObject();

            if (valueObject.has("kind")) {
                return true;
            }
        }
        return false;
    }
}
