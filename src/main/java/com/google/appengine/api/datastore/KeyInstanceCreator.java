package com.google.appengine.api.datastore;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class KeyInstanceCreator implements InstanceCreator<Key> {

    @Override
    public Key createInstance(Type type) {
        return KeyFactory.createKey("a", 1l);
    }

}
