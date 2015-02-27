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

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.testutil.GaeTestBase;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import static org.junit.Assert.assertEquals;

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
    public void getEntity_entityStored_shouldReturnSameEntity() throws EntityNotFoundException {
        // given
        Entity sentEntity = createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        Long entityId = sentEntity.getKey().getId();

        // when
        Entity savedEntity = getEntityFromEntityResource(entityId);

        // then
        assertEquals(sentEntity, savedEntity);
    }

    @Test
    public void updateEntity_entityStored_shouldUpdateEntity() throws EntityNotFoundException {
        // given
        Entity sentEntity = createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        Long entityId = sentEntity.getKey().getId();

        // when
        sentEntity.setProperty(PROPERTY_NAME, ANOTHER_NAME);
        entityService.updateEntity(sentEntity);

        // then
        Entity savedEntity = getEntityFromEntityResource(entityId);

        assertEquals(ANOTHER_NAME, savedEntity.getProperty(PROPERTY_NAME));
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteEntity_entityStored_shouldDeleteEntity() throws EntityNotFoundException {
        // given
        Entity sentEntity = createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        Key entityKey = sentEntity.getKey();
        Long entityId = entityKey.getId();

        // when
        entityService.deleteEntity(entityKey);

        // then
        getEntityFromEntityResource(entityId);
    }

    private Entity getEntityFromEntityResource(Long id) throws EntityNotFoundException {
        Key key = KeyFactory.createKey(KIND_NAME, id);

        return entityService.getEntity(KeyFactory.keyToString(key));
    }
}
