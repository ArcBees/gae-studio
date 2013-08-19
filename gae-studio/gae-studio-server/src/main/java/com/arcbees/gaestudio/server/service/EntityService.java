package com.arcbees.gaestudio.server.service;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public interface EntityService {
    Entity getEntity(Long entityId, String namespace, String appId, String kind, String parentId, String parentKind);

    Entity updateEntity(Entity entity);

    void deleteEntity(Key entityKey);
}
