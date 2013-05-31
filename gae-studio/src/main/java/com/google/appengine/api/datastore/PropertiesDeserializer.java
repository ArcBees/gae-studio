/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.google.appengine.api.datastore;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PropertiesDeserializer implements JsonDeserializer<Map> {
    @Override
    @SuppressWarnings("unchecked")
    public Map deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        Map<String, Object> properties = new HashMap<String, Object>();

        for (Map.Entry<String, JsonElement> property : json.getAsJsonObject().entrySet()) {
            String key = property.getKey();
            Object value;
            JsonElement jsonValueElement = property.getValue();

            if (isKey(jsonValueElement)) {
                value = context.deserialize(jsonValueElement, Key.class);
            } else {
                value = context.deserialize(jsonValueElement, Object.class);
            }

            properties.put(key, value);
        }

        return new LinkedHashMap(properties);
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
