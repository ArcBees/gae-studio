/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.visualizer;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import com.arcbees.gaestudio.shared.DeleteEntities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public interface EntitiesService {
    Iterable<Entity> getEntities(String kind, String namespace, Integer offset, Integer limit);

    Collection<Entity> getEntities(List<Key> keys);

    Entity createEmptyEntity(String kind);

    void deleteEntities(String kind, String namespace, DeleteEntities deleteType, String encodedKeys);

    long getCount(String kind, String namespace);

    List<Key> put(Iterable<Entity> entities);

    Future<List<Key>> putAsync(Iterable<Entity> entities);
}
