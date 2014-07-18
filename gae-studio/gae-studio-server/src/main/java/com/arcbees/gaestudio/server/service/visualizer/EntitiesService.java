/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.visualizer;

import java.util.List;

import com.arcbees.gaestudio.shared.DeleteEntities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public interface EntitiesService {
    Iterable<Entity> getEntities(String kind, Integer offset, Integer limit);

    java.util.Collection<Entity> getEntities(List<Key> keys);

    Entity createEmptyEntity(String kind);

    void deleteEntities(String kind, String namespace, DeleteEntities deleteType, String encodedKeys);

    Integer getCount(String kind);

    java.util.List<com.google.appengine.api.datastore.Key> put(Iterable<Entity> entities);
}
