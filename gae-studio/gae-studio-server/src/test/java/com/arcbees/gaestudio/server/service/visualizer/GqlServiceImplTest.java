/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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

        Collection<Entity> result = gqlService.executeGqlRequest("SELECT * FROM Autos WHERE make = 'Canada'", null, null);

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
