package com.google.appengine.api.datastore;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public class AppIdNamespaceInstanceCreator implements InstanceCreator<AppIdNamespace> {

    @Override
    public AppIdNamespace createInstance(Type type) {
        return KeyFactory.createKey("a", 1l).getAppIdNamespace();
    }

}
