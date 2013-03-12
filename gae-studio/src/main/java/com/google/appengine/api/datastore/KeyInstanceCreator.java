package com.google.appengine.api.datastore;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

public class KeyInstanceCreator implements InstanceCreator<Key> {
    @Override
    public Key createInstance(Type type) {
        return KeyFactory.createKey("a", 1l);
    }
}
