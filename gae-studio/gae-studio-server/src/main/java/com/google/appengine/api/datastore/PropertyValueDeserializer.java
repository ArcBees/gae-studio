/**
 * Copyright 2011 ArcBees Inc.
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
import java.util.Date;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.appengine.api.datastore.Entity.UnindexedValue;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import static com.arcbees.gaestudio.shared.PropertyName.GAE_PROPERTY_TYPE;

public class PropertyValueDeserializer implements JsonDeserializer<PropertyValue> {
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
                json = json.getAsJsonObject().get(UnindexedValueAdapter.VALUE);
            }

            switch (propertyType) {
                case NUMERIC:
                    value = context.deserialize(json, Long.class);
                    break;
                case FLOATING:
                    value = context.deserialize(json, Double.class);
                    break;
                case STRING:
                    value = context.deserialize(json, String.class);
                    // TODO: if > 500 char, convert to Text
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

    private PropertyType extractPropertyType(JsonElement jsonElement) {
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
