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

package com.arcbees.gaestudio.testutil;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalBlobstoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class GaeTestBase {
    private static final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig(), new LocalSearchServiceTestConfig(),
            new LocalBlobstoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    public Entity createEntityInDatastore(String kindName, String propertyName, Object value) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Entity entity = new Entity(kindName);

        entity.setProperty(propertyName, value);
        datastoreService.put(entity);

        return entity;
    }

    public Entity createEntityInNamespace(String namespace,
            String kindName,
            String propertyName,
            Object value) {
        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(namespace);

        Entity entity = createEntityInDatastore(kindName, propertyName, value);

        NamespaceManager.set(defaultNamespace);

        return entity;
    }
}
