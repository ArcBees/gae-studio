package com.google.appengine.api.datastore;

import java.lang.reflect.Modifier;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.storage.onestore.v3.OnestoreEntity;

public class GsonDatastoreFactory {
    private static ExclusionStrategy entityProtoExclusionStrategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getDeclaredClass().equals(OnestoreEntity.EntityProto.class);
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };

    public static Gson create() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.serializeNulls();
        gsonBuilder.excludeFieldsWithModifiers(Modifier.STATIC);
        gsonBuilder.setExclusionStrategies(entityProtoExclusionStrategy);
        gsonBuilder.registerTypeAdapter(Entity.class, new EntityInstanceCreator());
        gsonBuilder.registerTypeAdapter(AppIdNamespace.class, new AppIdNamespaceInstanceCreator());
        gsonBuilder.registerTypeAdapter(Key.class, new KeyInstanceCreator());
        gsonBuilder.registerTypeAdapter(Map.class, new PropertiesDeserializer());

        return gsonBuilder.create();
    }
}
