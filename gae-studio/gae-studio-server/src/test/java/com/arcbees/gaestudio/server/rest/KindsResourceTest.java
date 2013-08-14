package com.arcbees.gaestudio.server.rest;

import java.util.List;

import javax.inject.Inject;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.testutil.GaeTestBase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JukitoRunner.class)
public class KindsResourceTest extends GaeTestBase {
    private static final String KIND_NAME = "FakeEntity";
    private static final String ANOTHER_KIND = "FooEntity";
    private static final String PROPERTY_NAME = "property-name";
    private static final String A_NAME = "a-name";
    private static final String ANOTHER_NAME = "another-name";

    @Inject
    KindsResource kindsResource;

    @Test
    public void twoKindsStored_getKinds_shouldReturnTheTwoKinds() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(ANOTHER_KIND, PROPERTY_NAME, ANOTHER_NAME);

        //when
        List<String> kindsList = kindsResource.getKinds();

        //then
        assertEquals(2, kindsList.size());
        assertTrue(kindsList.contains(KIND_NAME));
        assertTrue(kindsList.contains(ANOTHER_KIND));
    }
}
