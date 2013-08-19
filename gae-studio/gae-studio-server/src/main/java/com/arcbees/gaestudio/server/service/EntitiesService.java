package com.arcbees.gaestudio.server.service;

import com.arcbees.gaestudio.shared.DeleteEntities;
import com.google.appengine.api.datastore.Entity;

public interface EntitiesService {
    Iterable<Entity> getEntities(String kind, Integer offset, Integer limit);

    Entity createEmptyEntity(String kind);

    void deleteEntities(String kind, String namespace, DeleteEntities deleteType);

    Integer getCount(String kind);
}
