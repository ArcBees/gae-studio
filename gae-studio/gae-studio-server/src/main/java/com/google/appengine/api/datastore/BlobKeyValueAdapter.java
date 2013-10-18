package com.google.appengine.api.datastore;

import java.lang.reflect.Type;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BlobKeyValueAdapter implements JsonSerializer<BlobKey>, JsonDeserializer<BlobKey> {
    @Override
    public BlobKey deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String value = context.deserialize(json, String.class);

        return new BlobKey(value);
    }

    @Override
    public JsonElement serialize(BlobKey src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getKeyString());
    }
}
