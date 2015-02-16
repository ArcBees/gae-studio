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

import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.testutil.GaeTestBase;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class NamespacesServiceImplTest extends GaeTestBase {
    public static class NamespacesServiceModule extends JukitoModule {
        @Override
        protected void configureTest() {
            bind(NamespacesService.class).to(NamespacesServiceImpl.class).in(TestSingleton.class);
        }
    }

    private static final String A_NAMESPACE = "a-namespace";
    private static final String ANOTHER_NAMESPACE = "another-namespace";
    private static final String KIND_NAME = "FakeEntity";
    private static final String PROPERTY_NAME = "property-name";
    private static final String A_NAME = "a-name";
    private static final String ANOTHER_NAME = "another-name";

    @Inject
    NamespacesService namespacesService;

    @Test
    public void getNamespaces_twoNamespacesStored_shouldReturnTheTwoNamespaces() {
        // given
        createEntityInNamespace(A_NAMESPACE, KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInNamespace(ANOTHER_NAMESPACE, KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        // when
        List<AppIdNamespaceDto> namespaceDtoList = namespacesService.getNamespaces();

        // then
        assertEquals(2, namespaceDtoList.size());
        assertEquals(A_NAMESPACE, namespaceDtoList.get(0).getNamespace());
        assertEquals(ANOTHER_NAMESPACE, namespaceDtoList.get(1).getNamespace());
    }
}
