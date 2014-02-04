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
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CollectionValueAdapter implements JsonSerializer<Collection<?>>, JsonDeserializer<Collection<?>> {
    @Override
    public Collection<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        List<Object> list = Lists.newArrayList();

        for (JsonElement element : json.getAsJsonArray()) {
            PropertyValue propertyValue = context.deserialize(element, PropertyValue.class);
            list.add(propertyValue.getValue());
        }

        return list;
    }

    @Override
    public JsonElement serialize(Collection<?> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray array = new JsonArray();

        for (Object item : src) {
            JsonElement serializedValue = context.serialize(new PropertyValue(item), PropertyValue.class);
            array.add(serializedValue);
        }

        return array;
    }
}
