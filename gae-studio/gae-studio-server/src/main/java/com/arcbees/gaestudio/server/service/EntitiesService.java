/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

import com.arcbees.gaestudio.shared.DeleteEntities;
import com.google.appengine.api.datastore.Entity;

public interface EntitiesService {
    Iterable<Entity> getEntities(String kind, Integer offset, Integer limit);

    Entity createEmptyEntity(String kind);

    void deleteEntities(String kind, String namespace, DeleteEntities deleteType);

    Integer getCount(String kind);
}
