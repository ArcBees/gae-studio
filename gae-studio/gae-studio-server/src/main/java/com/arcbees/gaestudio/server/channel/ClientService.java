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
            return new HashSet<String>();
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
