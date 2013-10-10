package com.arcbees.gaestudio.server.rest;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KindsResourceIT extends RestIT {
    @Test
    public void createObject_getKinds_KindIsReturned() {
        //given
        createRemoteCar();

        //when
        List<String> kinds = getRemoteKinds();

        //then
        assertEquals(1, kinds.size());
        assertEquals("Car", kinds.get(0));
    }
}
