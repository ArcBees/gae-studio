package com.google.appengine.api.datastore;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PropertiesDeserializer implements JsonDeserializer<Map> {

    @Override
    public Map deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
            JsonParseException {
        Map<String, Object> properties = new HashMap<String, Object>();

        for (Map.Entry<String, JsonElement> property : json.getAsJsonObject().entrySet()) {
            String key = property.getKey();
            Object value = null;
            JsonElement jsonValueElement = property.getValue();
            if (jsonValueElement.isJsonObject()) {
                JsonObject valueObject = jsonValueElement.getAsJsonObject();

                if (valueObject.has("kind")) {
                    value = context.deserialize(json, Key.class);
                }
            }

            if (value == null) {
                value = context.deserialize(jsonValueElement, Object.class);
            }

            properties.put(key, value);
        }

        return new LinkedHashMap(properties);
    }

}
