package com.arcbees.gaestudio.server.analytic;

import java.util.UUID;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.inject.Provider;

import static com.arcbees.gaestudio.server.GaeStudioConstants.GAE_NAMESPACE;
import static com.arcbees.gaestudio.server.GaeStudioConstants.GA_CLIENT_KIND;

public class GoogleAnalyticClientIdProvider implements Provider<String> {
    private static final String CLIENT_ID = "clientId";

    @Override
    public String get() {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(GAE_NAMESPACE);

        Query query = new Query(GA_CLIENT_KIND);
        Entity entity = datastoreService.prepare(query).asSingleEntity();

        String clientId;
        if (entity == null) {
            entity = new Entity(GA_CLIENT_KIND);
            clientId = UUID.randomUUID().toString();
            entity.setProperty(CLIENT_ID, clientId);
            datastoreService.put(entity);
        } else {
            clientId = entity.getProperty(CLIENT_ID).toString();
        }

        NamespaceManager.set(defaultNamespace);

        return clientId;
    }
}
