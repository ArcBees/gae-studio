/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.Iterator;

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.service.EntitiesService;
import com.arcbees.gaestudio.server.service.EntitiesServiceImpl;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.testutil.GaeTestBase;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(JukitoRunner.class)
public class EntitiesServiceImplTest extends GaeTestBase {
    @SuppressWarnings("unused")
    public static class EntitiesServiceModule extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(EntitiesService.class).to(EntitiesServiceImpl.class).in(TestSingleton.class);
        }
    }

    private static final String KIND_NAME = "FakeEntity";
    private static final String GAE_KIND_NAME = "__FakeEntity";
    private static final String PROPERTY_NAME = "property-name";
    private static final String A_NAME = "a-name";
    private static final String ANOTHER_NAME = "another-name";

    @Inject
    EntitiesService entitiesService;

    @Test
    public void twoEntitiesStored_getEntities_shouldReturnTwoEntities() {
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
    public void entityStored_createEmptyEntity_shouldReturnEmptyEntity() throws EntityNotFoundException {
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
    public void twoEntitiesStored_deleteEntitiesByKind_shouldHaveNoMoreEntities() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        entitiesService.deleteEntities(KIND_NAME, null, DeleteEntities.KIND);

        //then
        assertEquals(0l, (long) entitiesService.getCount(KIND_NAME));
    }

    @Test
    public void twoEntitiesStored_deleteEntitiesByNamespace_shouldHaveOneMoreEntities() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(GAE_KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        entitiesService.deleteEntities(null, "", DeleteEntities.NAMESPACE);

        //then
        assertEquals(1l, (long) entitiesService.getCount(GAE_KIND_NAME));
    }

    @Test
    public void twoEntitiesStored_getCount_shouldReturnTwoEntities() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        Integer entityCount = entitiesService.getCount(KIND_NAME);

        //then
        assertEquals(2l, (long) entityCount);
    }
}
