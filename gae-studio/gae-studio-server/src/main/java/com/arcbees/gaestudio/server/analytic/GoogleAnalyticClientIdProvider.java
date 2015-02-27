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
import static com.arcbees.gaestudio.shared.Constants.CLIENT_ID;

public class GoogleAnalyticClientIdProvider implements Provider<String> {
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
