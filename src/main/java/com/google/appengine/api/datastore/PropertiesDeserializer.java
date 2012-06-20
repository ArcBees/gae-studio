package com.google.appengine.api.datastore;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

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
