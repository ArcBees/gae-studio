package com.google.appengine.api.datastore;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

public class AppIdNamespaceInstanceCreator implements InstanceCreator<AppIdNamespace> {
    @Override
    public AppIdNamespace createInstance(Type type) {
        return KeyFactory.createKey("a", 1l).getAppIdNamespace();
    }
}
