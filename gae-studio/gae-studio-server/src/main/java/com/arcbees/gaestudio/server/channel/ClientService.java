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

package com.arcbees.gaestudio.server.channel;

import java.util.HashSet;
import java.util.Set;

import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class ClientService {
    private final MemcacheService memcacheService;

    ClientService() {
        this.memcacheService = MemcacheServiceFactory.getMemcacheService();
    }

    public Set<String> getClientIds() {
        Object object = memcacheService.get(GaeStudioConstants.GAESTUDIO_OPERATIONS_CLIENT_IDS);

        if (object == null) {
            return new HashSet<>();
        } else {
            return (Set<String>) object;
        }
    }

    public void removeClient(String clientId) {
        Set<String> clientIds = getClientIds();

        clientIds.remove(clientId);

        save(clientIds);
    }

    public void storeClient(String clientId) {
        Set<String> clientIds = getClientIds();

        clientIds.add(clientId);

        save(clientIds);
    }

    private void save(Set<String> clientIds) {
        memcacheService.put(GaeStudioConstants.GAESTUDIO_OPERATIONS_CLIENT_IDS, clientIds);
    }
}
