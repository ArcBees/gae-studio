package com.arcbees.gaestudio.server.rest;

import java.util.List;

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.jukito.TestSingleton;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.service.NamespacesService;
import com.arcbees.gaestudio.server.service.NamespacesServiceImpl;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.testutil.GaeTestBase;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class NamespacesServiceImplTest extends GaeTestBase {
    @SuppressWarnings("unused")
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
    public void twoNamespacesStored_getNamespaces_shouldReturnTheTwoNamespaces() {
        //given
        createEntityInNamespace(A_NAMESPACE, KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInNamespace(ANOTHER_NAMESPACE, KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        List<AppIdNamespaceDto> namespaceDtoList = namespacesService.getNamespaces();

        //then
        assertEquals(2, namespaceDtoList.size());
        assertEquals(A_NAMESPACE, namespaceDtoList.get(0).getNamespace());
        assertEquals(ANOTHER_NAMESPACE, namespaceDtoList.get(1).getNamespace());
    }
}
