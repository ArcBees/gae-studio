/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.List;

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.service.KindsService;
import com.arcbees.gaestudio.server.service.KindsServiceImpl;
import com.arcbees.gaestudio.testutil.GaeTestBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JukitoRunner.class)
public class KindsServiceImplTest extends GaeTestBase {
    public static class KindsServiceModule extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(KindsService.class).to(KindsServiceImpl.class).in(TestSingleton.class);
        }
    }

    private static final String KIND_NAME = "FakeEntity";
    private static final String GAE_KIND_NAME = "__FakeEntity";
    private static final String ANOTHER_KIND = "FooEntity";
    private static final String PROPERTY_NAME = "property-name";
    private static final String A_NAME = "a-name";
    private static final String ANOTHER_NAME = "another-name";

    @Inject
    KindsService kindsService;

    @Test
    public void twoKindsStored_getKinds_shouldReturnTheTwoKinds() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(ANOTHER_KIND, PROPERTY_NAME, ANOTHER_NAME);

        //when
        List<String> kindsList = kindsService.getKinds();

        //then
        assertEquals(2, kindsList.size());
        assertTrue(kindsList.contains(KIND_NAME));
        assertTrue(kindsList.contains(ANOTHER_KIND));
    }

    @Test
    public void threeKindsStored_gaeKind_getKinds_shouldReturnTheTwoKinds() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(ANOTHER_KIND, PROPERTY_NAME, ANOTHER_NAME);
        createEntityInDatastore(GAE_KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        List<String> kindsList = kindsService.getKinds();

        //then
        assertEquals(2, kindsList.size());
        assertTrue(kindsList.contains(KIND_NAME));
        assertTrue(kindsList.contains(ANOTHER_KIND));
    }
}
