/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
