/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.appengine.api.datastore;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PropertiesValueAdapter implements JsonDeserializer<Map<?, ?>>, JsonSerializer<Map<?, ?>> {
    @Override
    public Map<?, ?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Map<String, Object> properties = Maps.newLinkedHashMap();

        for (Map.Entry<String, JsonElement> jsonProperty : json.getAsJsonObject().entrySet()) {
            String key = jsonProperty.getKey();
            PropertyValue propertyValue = context.deserialize(jsonProperty.getValue(), PropertyValue.class);

            properties.put(key, propertyValue == null ? null : propertyValue.getValue());
        }

        return properties;
    }

    @Override
    public JsonElement serialize(Map<?, ?> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject map = new JsonObject();

        for (Entry<?, ?> entry : src.entrySet()) {
            JsonElement serializedValue = context.serialize(new PropertyValue(entry.getValue()), PropertyValue.class);
            map.add(String.valueOf(entry.getKey()), serializedValue);
        }

        return map;
    }
}
