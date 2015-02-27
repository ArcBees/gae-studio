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

import java.util.Iterator;

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.util.DatastoreCountProvider;
import com.arcbees.gaestudio.server.util.DevServerDatastoreCountProvider;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.testutil.GaeTestBase;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JukitoRunner.class)
public class EntitiesServiceImplTest extends GaeTestBase {
    public static class EntitiesServiceModule extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(EntitiesService.class).to(EntitiesServiceImpl.class).in(TestSingleton.class);
            bind(DatastoreCountProvider.class).to(DevServerDatastoreCountProvider.class).in(TestSingleton.class);
        }
    }

    private static final String ALL_NAMESPACES = null;
    private static final String KIND_NAME = "FakeEntity";
    private static final String PROPERTY_NAME = "property-name";
    private static final String UNINDEXED_PROPERTY_NAME = "unindexed-property-name";
    private static final String A_NAME = "a-name";
    private static final String ANOTHER_NAME = "another-name";
    private static final String A_NAMESPACE = "a-namespace";
    private static final String DEFAULT_NAMESPACE = "";

    @Inject
    EntitiesService entitiesService;

    @Test
    public void getEntities_twoEntitiesStored_shouldReturnTwoEntities() {
        // given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        // when
        Iterable<Entity> entities = entitiesService.getEntities(KIND_NAME, ALL_NAMESPACES, null, null);

        // then
        Iterator<Entity> it = entities.iterator();

        Entity entity1 = it.next();
        Entity entity2 = it.next();

        assertEquals(A_NAME, entity1.getProperty(PROPERTY_NAME));
        assertEquals(ANOTHER_NAME, entity2.getProperty(PROPERTY_NAME));
        assertFalse(it.hasNext());
    }

    @Test
    public void createEmptyEntity_entityStored_shouldReturnEmptyEntity()
            throws EntityNotFoundException, InstantiationException, IllegalAccessException {
        // given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);

        // when
        Entity entity = entitiesService.createEmptyEntity(KIND_NAME);

        // then
        assertNotNull(entity);
        assertEquals(KIND_NAME, entity.getKind());
        assertEquals("", entity.getProperty(PROPERTY_NAME));
    }

    @Test
    public void createEmptyEntity_entityStored_shouldKeepIndexes()
            throws EntityNotFoundException, InstantiationException, IllegalAccessException {
        // given
        createEntityWithMultipleProperties();

        // when
        Entity entity = entitiesService.createEmptyEntity(KIND_NAME);

        // then
        assertNotNull(entity);
        assertEquals(KIND_NAME, entity.getKind());
        assertEquals("", entity.getProperty(PROPERTY_NAME));
        assertFalse(entity.isUnindexedProperty(PROPERTY_NAME));
        assertEquals("", entity.getProperty(UNINDEXED_PROPERTY_NAME));
        assertTrue(entity.isUnindexedProperty(UNINDEXED_PROPERTY_NAME));
    }

    @Test
    public void deleteEntitiesByKind_twoEntitiesStored_shouldHaveNoMoreEntities() {
        // given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        // when
        entitiesService.deleteEntities(KIND_NAME, null, DeleteEntities.KIND, null);

        // then
        assertEquals(0L, entitiesService.getCount(KIND_NAME, ALL_NAMESPACES));
    }

    @Test
    public void deleteEntitiesByNamespace_twoEntitiesStored_shouldHaveOneMoreEntities() {
        // given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInNamespace(A_NAMESPACE, KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        // when
        entitiesService.deleteEntities(null, "", DeleteEntities.NAMESPACE, null);

        // then
        assertEquals(1L, entitiesService.getCount(KIND_NAME, ALL_NAMESPACES));
    }

    @Test
    public void getCount_withNamespace_shouldReturnOneEntity() {
        // given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInNamespace(A_NAMESPACE, KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        // when
        long entityCount = entitiesService.getCount(KIND_NAME, A_NAMESPACE);

        // then
        assertEquals(1L, entityCount);
    }

    @Test
    public void getCount_withDefaultNamespace_shouldReturnOneEntity() {
        // given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInNamespace(A_NAMESPACE, KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        // when
        long entityCount = entitiesService.getCount(KIND_NAME, DEFAULT_NAMESPACE);

        // then
        assertEquals(1L, entityCount);
    }

    @Test
    public void getCount_twoEntitiesStored_shouldReturnTwoEntities() {
        // given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        // when
        long entityCount = entitiesService.getCount(KIND_NAME, ALL_NAMESPACES);

        // then
        assertEquals(2L, entityCount);
    }

    private void createEntityWithMultipleProperties() {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Entity entity = new Entity(KIND_NAME);

        entity.setProperty(PROPERTY_NAME, A_NAME);
        entity.setUnindexedProperty(UNINDEXED_PROPERTY_NAME, A_NAME);
        datastoreService.put(entity);
    }
}
