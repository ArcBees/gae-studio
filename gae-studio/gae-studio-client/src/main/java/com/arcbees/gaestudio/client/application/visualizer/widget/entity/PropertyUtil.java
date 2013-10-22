/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

import static com.arcbees.gaestudio.shared.PropertyName.GAE_PROPERTY_TYPE;
import static com.arcbees.gaestudio.shared.PropertyName.INDEXED;
import static com.arcbees.gaestudio.shared.PropertyName.VALUE;

public class PropertyUtil {
    public static JSONValue getPropertyValue(JSONValue property) {
        JSONObject object = property.isObject();

        if (object != null && object.containsKey(VALUE)) {
            return object.get(VALUE);
        }

        return property;
    }

    public static Boolean isPropertyIndexed(JSONValue property) {
        JSONObject embed = property.isObject();

        if (embed != null && embed.containsKey(INDEXED)) {
            JSONBoolean indexed = embed.get(INDEXED).isBoolean();

            return indexed == null || indexed.booleanValue();
        }

        return true;
    }

    public static JSONValue cleanUpMetadata(JSONValue property) {
        JSONValue result = property;

        JSONObject object = property.isObject();
        if (object != null) {
            result = cleanUpObjectMetadata(object);
        }

        JSONArray array = property.isArray();
        if (array != null) {
            result = cleanUpArrayMetadata(array);
        }

        return result;
    }

    public static JSONValue parseJsonValueWithMetadata(JSONValue value, PropertyType type, Boolean indexed) {
        JSONObject wrapper = null;

        if (!indexed) {
            wrapper = new JSONObject();
            wrapper.put(INDEXED, JSONBoolean.getInstance(false));
        }

        if (type != PropertyType.NULL) {
            if (wrapper == null) {
                wrapper = new JSONObject();
            }

            wrapper.put(GAE_PROPERTY_TYPE, new JSONString(type.name()));
        }

        if (wrapper != null) {
            wrapper.put(VALUE, value);
            return wrapper;
        }

        return value;
    }

    public static PropertyType getPropertyType(JSONValue jsonValue) {
        PropertyType type = PropertyType.NULL;

        JSONObject asObject = jsonValue.isObject();
        if (asObject != null) {
            if (asObject.containsKey(GAE_PROPERTY_TYPE)) {
                JSONString typeName = asObject.get(GAE_PROPERTY_TYPE).isString();
                type = PropertyType.valueOf(typeName.stringValue());

                jsonValue = getPropertyValue(jsonValue);
            } else if (asObject.containsKey(INDEXED)) {
                jsonValue = getPropertyValue(jsonValue);
            }
        }

        if (type == PropertyType.NULL) {
            type = guessPropertyType(jsonValue);
        }

        return type;
    }

    public static String getPropertyAsString(JSONObject object, String propertyName) {
        JSONValue property = object.get(propertyName);
        if (property != null && property.isNull() == null) {
            return property.isString().stringValue();
        }

        return "";
    }

    private static PropertyType guessPropertyType(JSONValue jsonValue) {
        PropertyType type;
        if (jsonValue.isString() != null) {
            type = PropertyType.STRING;
        } else if (jsonValue.isNumber() != null) {
            type = PropertyType.NUMERIC;
        } else if (jsonValue.isBoolean() != null) {
            type = PropertyType.BOOLEAN;
        } else {
            type = PropertyType.NULL;
        }
        return type;
    }

    private static JSONValue cleanUpObjectMetadata(JSONObject object) {
        JSONValue result;

        if (object.containsKey(VALUE)) {
            result = cleanUpMetadata(object.get(VALUE));
        } else {
            JSONObject cleanedUpObject = new JSONObject();

            for (String key : object.keySet()) {
                JSONValue cleanedUpElement = cleanUpMetadata(object.get(key));
                cleanedUpObject.put(key, cleanedUpElement);
            }

            result = cleanedUpObject;
        }

        return result;
    }

    private static JSONValue cleanUpArrayMetadata(JSONArray array) {
        JSONArray cleanedUpArray = new JSONArray();
        int size = array.size();

        for (int i = 0; i < size; ++i) {
            JSONValue cleanedUpElement = cleanUpMetadata(array.get(i));
            cleanedUpArray.set(i, cleanedUpElement);
        }

        return cleanedUpArray;
    }
}
