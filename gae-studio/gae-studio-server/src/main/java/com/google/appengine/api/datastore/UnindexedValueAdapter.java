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

import com.arcbees.gaestudio.server.util.JsonUtil;
import com.google.appengine.api.datastore.Entity.UnindexedValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import static com.arcbees.gaestudio.shared.PropertyName.INDEXED;
import static com.arcbees.gaestudio.shared.PropertyName.VALUE;

public class UnindexedValueAdapter implements JsonSerializer<UnindexedValue>, JsonDeserializer<UnindexedValue> {
    public static boolean isUnindexedValue(Object value) {
        return value instanceof UnindexedValue;
    }

    public static boolean isUnindexedValue(JsonElement element) {
        return !isIndexedValue(element);
    }

    public static boolean isIndexedValue(JsonElement element) {
        return !element.isJsonObject() // Not an object, so it's not wrapped by UnindexedValue: indexed by default
               || !element.getAsJsonObject().has(INDEXED) // No indexed property: indexed by default
               || element.getAsJsonObject().get(INDEXED).getAsBoolean();
    }

    @Override
    public JsonElement serialize(UnindexedValue unindexedValue, Type type, JsonSerializationContext context) {
        JsonElement value = context.serialize(new PropertyValue(unindexedValue.getValue()), PropertyValue.class);

        JsonObject object;
        if (JsonUtil.hasEmbedValue(value)) {
            object = value.getAsJsonObject();
        } else {
            object = new JsonObject();
            object.add(VALUE, value);
        }

        object.addProperty(INDEXED, false);

        return object;
    }

    @Override
    public UnindexedValue deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws
            JsonParseException {
        if (!isUnindexedValue(jsonElement)) {
            throw new IllegalArgumentException("The Json element doesn't represent an unindexed value: " + jsonElement);
        }

        jsonElement.getAsJsonObject().remove(INDEXED);

        PropertyValue propertyValue = context.deserialize(jsonElement, PropertyValue.class);
        return new UnindexedValue(propertyValue.getValue());
    }
}
