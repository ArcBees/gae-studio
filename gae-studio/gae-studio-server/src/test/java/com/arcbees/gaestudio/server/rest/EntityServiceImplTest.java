/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.service.EntityService;
import com.arcbees.gaestudio.server.service.EntityServiceImpl;
import com.arcbees.gaestudio.testutil.GaeTestBase;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(JukitoRunner.class)
public class EntityServiceImplTest extends GaeTestBase {
    public static class EntityServiceModule extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(EntityService.class).to(EntityServiceImpl.class).in(TestSingleton.class);
        }
    }

    private static final String KIND_NAME = "FakeEntity";
    private static final String PROPERTY_NAME = "property-name";
    private static final String A_NAME = "a-name";
    private static final String ANOTHER_NAME = "another-name";

    @Inject
    EntityService entityService;

    @Test
    public void entityStored_getEntity_shouldReturnSameEntity() throws EntityNotFoundException {
        //given
        Entity sentEntity = createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        Long entityId = sentEntity.getKey().getId();

        //when
        Entity savedEntity = getEntityFromEntityResource(entityId);

        //then
        assertEquals(sentEntity, savedEntity);
    }

    @Test
    public void entityStored_updateEntity_shouldUpdateEntity() throws EntityNotFoundException {
        //given
        Entity sentEntity = createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        Long entityId = sentEntity.getKey().getId();

        //when
        sentEntity.setProperty(PROPERTY_NAME, ANOTHER_NAME);
        entityService.updateEntity(sentEntity);

        //then
        Entity savedEntity = getEntityFromEntityResource(entityId);

        assertEquals(ANOTHER_NAME, savedEntity.getProperty(PROPERTY_NAME));
    }

    @Test
    public void entityStored_deleteEntity_shouldDeleteEntity() throws EntityNotFoundException {
        //given
        Entity sentEntity = createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        Key entityKey = sentEntity.getKey();
        Long entityId = entityKey.getId();

        //when
        entityService.deleteEntity(entityKey);

        //then
        assertNull(getEntityFromEntityResource(entityId));
    }

    private Entity getEntityFromEntityResource(Long id) {
        return entityService.getEntity(id, null, null, KIND_NAME, null, null);
    }
}
