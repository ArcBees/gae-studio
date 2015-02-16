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
        // given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(ANOTHER_KIND, PROPERTY_NAME, ANOTHER_NAME);

        // when
        List<String> kindsList = kindsService.getKinds(null);

        // then
        assertEquals(2, kindsList.size());
        assertTrue(kindsList.contains(KIND_NAME));
        assertTrue(kindsList.contains(ANOTHER_KIND));
    }

    @Test
    public void getKinds_withNamespace_shouldReturnOnlyOneKind() {
        // given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInNamespace(SOME_NAMESPACE, ANOTHER_KIND, PROPERTY_NAME, ANOTHER_NAME);

        // when
        List<String> kindsList = kindsService.getKinds(SOME_NAMESPACE);

        // then
        assertEquals(1, kindsList.size());
        assertTrue(kindsList.contains(ANOTHER_KIND));
    }

    @Test
    public void getKinds_withDefaultNamespace_shouldReturnOnlyOneKind() {
        // given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInNamespace(SOME_NAMESPACE, ANOTHER_KIND, PROPERTY_NAME, ANOTHER_NAME);

        // when
        List<String> kindsList = kindsService.getKinds("");

        // then
        assertEquals(1, kindsList.size());
        assertTrue(kindsList.contains(KIND_NAME));
    }

    @Test
    public void getKinds_threeKindsStored_gaeKind_shouldReturnTheTwoKinds() {
        // given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(ANOTHER_KIND, PROPERTY_NAME, ANOTHER_NAME);
        createEntityInDatastore(GAE_KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        // when
        List<String> kindsList = kindsService.getKinds(null);

        // then
        assertEquals(2, kindsList.size());
        assertTrue(kindsList.contains(KIND_NAME));
        assertTrue(kindsList.contains(ANOTHER_KIND));
    }
}
