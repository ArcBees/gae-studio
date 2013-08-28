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

import com.google.appengine.api.datastore.Entity.UnindexedValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UnindexedValueAdapter implements JsonSerializer<UnindexedValue>, JsonDeserializer<UnindexedValue> {
    static final String INDEXED = "__indexed";
    static final String VALUE = "value";

    public static boolean isUnindexedValue(JsonElement element) {
        return element.isJsonObject() && element.getAsJsonObject().has(INDEXED) && !element.getAsJsonObject()
                .get(INDEXED).getAsBoolean();
    }

    public static boolean isIndexedValue(JsonElement element) {
        return element.isJsonObject() && element.getAsJsonObject().has(INDEXED) && element.getAsJsonObject()
                .get(INDEXED).getAsBoolean();
    }

    @Override
    public JsonElement serialize(UnindexedValue unindexedValue, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty(INDEXED, false);
        object.add(VALUE, context.serialize(unindexedValue.getValue()));
        return object;
    }

    @Override
    public UnindexedValue deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws
            JsonParseException {
        if (!isUnindexedValue(jsonElement)) {
            throw new IllegalArgumentException("The Json element doesn't represent an unindexed value : " + jsonElement
                    .toString());
        }

        return new UnindexedValue(context.deserialize(jsonElement.getAsJsonObject().get(VALUE), Object.class));
    }
}
