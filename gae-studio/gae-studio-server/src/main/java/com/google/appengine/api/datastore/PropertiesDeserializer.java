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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class PropertiesDeserializer implements JsonDeserializer<Map> {
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
}
