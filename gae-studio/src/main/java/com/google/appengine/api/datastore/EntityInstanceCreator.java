package com.google.appengine.api.datastore;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

public class EntityInstanceCreator implements InstanceCreator<Entity> {
    @Override
    public Entity createInstance(Type type) {
        return new Entity("a");
    }
}
