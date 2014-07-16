/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.visualizer;

import java.util.Iterator;

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

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
        }
    }

    private static final String KIND_NAME = "FakeEntity";
    private static final String GAE_KIND_NAME = "__FakeEntity";
    private static final String PROPERTY_NAME = "property-name";
    private static final String UNINDEXED_PROPERTY_NAME = "unindexed-property-name";
    private static final String A_NAME = "a-name";
    private static final String ANOTHER_NAME = "another-name";
    private static final String A_NAMESPACE = "a-namespace";

    @Inject
    EntitiesService entitiesService;

    @Test
    public void getEntities_twoEntitiesStored_shouldReturnTwoEntities() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        Iterable<Entity> entities = entitiesService.getEntities(KIND_NAME, null, null);

        //then
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
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);

        //when
        Entity entity = entitiesService.createEmptyEntity(KIND_NAME);

        //then
        assertNotNull(entity);
        assertEquals(KIND_NAME, entity.getKind());
        assertEquals("", entity.getProperty(PROPERTY_NAME));
    }

    @Test
    public void createEmptyEntity_entityStored_shouldKeepIndexes()
            throws EntityNotFoundException, InstantiationException, IllegalAccessException {
        //given
        createEntityWithMultipleProperties();

        //when
        Entity entity = entitiesService.createEmptyEntity(KIND_NAME);

        //then
        assertNotNull(entity);
        assertEquals(KIND_NAME, entity.getKind());
        assertEquals("", entity.getProperty(PROPERTY_NAME));
        assertFalse(entity.isUnindexedProperty(PROPERTY_NAME));
        assertEquals("", entity.getProperty(UNINDEXED_PROPERTY_NAME));
        assertTrue(entity.isUnindexedProperty(UNINDEXED_PROPERTY_NAME));
    }

    @Test
    public void deleteEntitiesByKind_twoEntitiesStored_shouldHaveNoMoreEntities() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        entitiesService.deleteEntities(KIND_NAME, null, DeleteEntities.KIND, null);

        //then
        assertEquals(0l, (long) entitiesService.getCount(KIND_NAME));
    }

    @Test
    public void deleteEntitiesByNamespace_twoEntitiesStored_shouldHaveOneMoreEntities() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInNamespace(A_NAMESPACE, KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        entitiesService.deleteEntities(null, "", DeleteEntities.NAMESPACE, null);

        //then
        assertEquals(1l, (long) entitiesService.getCount(KIND_NAME));
    }

    @Test
    public void getCount_twoEntitiesStored_shouldReturnTwoEntities() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        Integer entityCount = entitiesService.getCount(KIND_NAME);

        //then
        assertEquals(2l, (long) entityCount);
    }

    private void createEntityWithMultipleProperties() {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Entity entity = new Entity(KIND_NAME);

        entity.setProperty(PROPERTY_NAME, A_NAME);
        entity.setUnindexedProperty(UNINDEXED_PROPERTY_NAME, A_NAME);
        datastoreService.put(entity);
    }
}
