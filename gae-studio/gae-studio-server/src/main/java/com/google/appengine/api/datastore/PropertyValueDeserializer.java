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

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.appengine.api.datastore.Entity.UnindexedValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import static com.arcbees.gaestudio.shared.PropertyName.GAE_PROPERTY_TYPE;
import static com.arcbees.gaestudio.shared.PropertyName.VALUE;

public class PropertyValueDeserializer implements JsonDeserializer<PropertyValue> {
    private static final int APPENGINE_STRING_MAX_LENGTH = 500;

    @Override
    public PropertyValue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Object value;
        if (isKey(json)) {
            // TODO: key should be identified by __gaePropertyType
            value = context.deserialize(json, Key.class);
        } else if (UnindexedValueAdapter.isUnindexedValue(json)) {
            value = context.deserialize(json, UnindexedValue.class);
        } else {
            PropertyType propertyType = extractPropertyType(json);

            if (propertyType != PropertyType.NULL) {
                json = json.getAsJsonObject().get(VALUE);
            }

            switch (propertyType) {
                case NUMERIC:
                    value = context.deserialize(json, Long.class);
                    break;
                case FLOATING:
                    value = context.deserialize(json, Double.class);
                    break;
                case STRING:
                    value = deserializeString(json, context);
                    break;
                case BOOLEAN:
                    value = context.deserialize(json, Boolean.class);
                    break;
                case DATE:
                    value = context.deserialize(json, Date.class);
                    break;
                default:
                    value = context.deserialize(json, Object.class);
            }
        }

        return new PropertyValue(value);
    }

    private Object deserializeString(JsonElement json, JsonDeserializationContext context) {
        String value = context.deserialize(json, String.class);

        if (value.length() > APPENGINE_STRING_MAX_LENGTH) {
            return new Text(value);
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
        }

        return propertyType;
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
