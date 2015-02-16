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

import java.util.Collection;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.testutil.GaeTestBase;
import com.google.appengine.api.datastore.Entity;
import com.google.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JukitoRunner.class)
public class GqlServiceImplTest extends GaeTestBase {
    public static class GqlServiceModule extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(GqlService.class).to(GqlServiceImpl.class).in(TestSingleton.class);
        }
    }

    @Inject
    GqlService gqlService;

    @Test
    public void executeSelectReturnAllEntities() {
        createCars();

        Collection<Entity> result = gqlService.executeGqlRequest("SELECT * FROM Car", null, null);

        assertEquals(3, result.size());
    }

    @Test
    public void executeWhereReturnOneRelevantEntity() {
        createCars();

        Collection<Entity> result = gqlService.executeGqlRequest("SELECT * FROM Car WHERE make = 'Canada'", null, null);

        assertEquals(1, result.size());
    }

    @Test
    public void executeWhereReturnZeroMatchedEntity() {
        createCars();

        Collection<Entity> result = gqlService.executeGqlRequest("SELECT * FROM Car WHERE make = 'Italy'", null, null);

        assertTrue(result.isEmpty());
    }

    @Test
    public void executeFromUnknowEntityZeroMatchedEntity() {
        createCars();

        Collection<Entity> result = gqlService.executeGqlRequest("SELECT * FROM Autos WHERE make = 'Canada'", null,
                null);

        assertTrue(result.isEmpty());
    }

    @Test
    public void executeRequestWithLimit() {
        createCars();

        Collection<Entity> result = gqlService.executeGqlRequest("SELECT * FROM Car", 0, 2);

        assertEquals(2, result.size());
    }

    @Test
    public void executeRequestWithDownLimit() {
        createCars();
        createCars();

        Collection<Entity> result = gqlService.executeGqlRequest("SELECT * FROM Car", 2, 4);

        assertEquals(4, result.size());
    }

    private void createCars() {
        createEntityInDatastore("Car", "make", "China");
        createEntityInDatastore("Car", "make", "Canada");
        createEntityInDatastore("Car", "make", "United States");
    }
}
