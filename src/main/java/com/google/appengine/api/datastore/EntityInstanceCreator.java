package com.google.appengine.api.datastore;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class EntityInstanceCreator implements InstanceCreator<Entity> {
    @Override
    public Entity createInstance(Type type) {
        return new Entity("a");
    }
}
