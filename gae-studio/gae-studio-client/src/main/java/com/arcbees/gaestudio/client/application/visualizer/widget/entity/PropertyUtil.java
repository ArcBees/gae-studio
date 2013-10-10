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

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

import static com.arcbees.gaestudio.shared.PropertyName.GAE_PROPERTY_TYPE;
import static com.arcbees.gaestudio.shared.PropertyName.INDEXED;

public class PropertyUtil {
    public static JSONValue getPropertyValue(JSONValue property) {
        JSONObject object = property.isObject();

        if (object != null && object.containsKey("value")) {
            return object.get("value");
        }

        return property;
    }

    public static Boolean isPropertyIndexed(JSONValue property) {
        JSONObject embed = property.isObject();

        if (embed != null && embed.containsKey(INDEXED.getPropertyName())) {
            JSONBoolean indexed = embed.get(INDEXED.getPropertyName()).isBoolean();

            return indexed == null || indexed.booleanValue();
        }

        return true;
    }

    public static JSONValue parseJsonValueWithMetadata(JSONValue value, PropertyType type, Boolean indexed) {
        JSONObject wrapper = null;

        if (!indexed) {
            wrapper = new JSONObject();
            wrapper.put(INDEXED.getPropertyName(), JSONBoolean.getInstance(false));
        }

        if (type != PropertyType.NULL) {
            if (wrapper == null) {
                wrapper = new JSONObject();
            }

            wrapper.put(GAE_PROPERTY_TYPE.getPropertyName(), new JSONString(type.name()));
        }

        if (wrapper != null) {
            wrapper.put("value", value);
            return wrapper;
        }

        return value;
    }

    public static JSONObject createUnindexedValue(JSONValue value) {
        JSONObject wrapper = new JSONObject();
        wrapper.put(INDEXED.getPropertyName(), JSONBoolean.getInstance(false));
        wrapper.put("value", value);

        return wrapper;
    }

    public static PropertyType getPropertyType(JSONValue jsonValue) {
        PropertyType type = PropertyType.NULL;

        JSONObject asObject = jsonValue.isObject();
        if (asObject != null) {
            if (asObject.containsKey(GAE_PROPERTY_TYPE.getPropertyName())) {
                JSONString typeName = asObject.get(GAE_PROPERTY_TYPE.getPropertyName()).isString();
                type = PropertyType.valueOf(typeName.stringValue());

                jsonValue = getPropertyValue(jsonValue);
            } else if (asObject.containsKey(INDEXED.getPropertyName())) {
                jsonValue = getPropertyValue(jsonValue);
            }
        }

        if (type == PropertyType.NULL) {
            type = guessPropertyType(jsonValue);
        }

        return type;
    }

    private static PropertyType guessPropertyType(JSONValue jsonValue) {
        PropertyType type;
        if (jsonValue.isArray() != null) {
            type = PropertyType.ARRAY;
        } else if (jsonValue.isString() != null) {
            type = PropertyType.STRING;
        } else if (jsonValue.isNumber() != null) {
            type = PropertyType.NUMERIC;
        } else if (jsonValue.isBoolean() != null) {
            type = PropertyType.BOOLEAN;
        } else if (jsonValue.isObject() != null) {
            type = PropertyType.OBJECT;
        } else {
            type = PropertyType.NULL;
        }
        return type;
    }
}
