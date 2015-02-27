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

        if (hasCollectionValue(object)) {
            object.addProperty(GAE_PROPERTY_TYPE, PropertyType.COLLECTION.name());
        }

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

    private boolean hasCollectionValue(JsonObject object) {
        return object.has(VALUE) && object.get(VALUE).isJsonArray();
    }
}
