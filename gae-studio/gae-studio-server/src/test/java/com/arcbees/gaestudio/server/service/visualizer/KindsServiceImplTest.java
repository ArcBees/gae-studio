/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.visualizer;

import java.util.List;

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    private static final String SOME_NAMESPACE = "SOME_NAMESPACE";

    @Inject
    KindsService kindsService;

    @Test
    public void getKinds_twoKindsStored_shouldReturnTheTwoKinds() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(ANOTHER_KIND, PROPERTY_NAME, ANOTHER_NAME);

        //when
        List<String> kindsList = kindsService.getKinds(null);

        //then
        assertEquals(2, kindsList.size());
        assertTrue(kindsList.contains(KIND_NAME));
        assertTrue(kindsList.contains(ANOTHER_KIND));
    }

    @Test
    public void getKinds_withNamespace_shouldReturnOnlyOneKind() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInNamespace(SOME_NAMESPACE, ANOTHER_KIND, PROPERTY_NAME, ANOTHER_NAME);

        //when
        List<String> kindsList = kindsService.getKinds(SOME_NAMESPACE);

        //then
        assertEquals(1, kindsList.size());
        assertTrue(kindsList.contains(ANOTHER_KIND));
    }

    @Test
    public void getKinds_withDefaultNamespace_shouldReturnOnlyOneKind() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInNamespace(SOME_NAMESPACE, ANOTHER_KIND, PROPERTY_NAME, ANOTHER_NAME);

        //when
        List<String> kindsList = kindsService.getKinds("");

        //then
        assertEquals(1, kindsList.size());
        assertTrue(kindsList.contains(KIND_NAME));
    }

    @Test
    public void getKinds_threeKindsStored_gaeKind_shouldReturnTheTwoKinds() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(ANOTHER_KIND, PROPERTY_NAME, ANOTHER_NAME);
        createEntityInDatastore(GAE_KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        List<String> kindsList = kindsService.getKinds(null);

        //then
        assertEquals(2, kindsList.size());
        assertTrue(kindsList.contains(KIND_NAME));
        assertTrue(kindsList.contains(ANOTHER_KIND));
    }
}
