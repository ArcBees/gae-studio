package com.arcbees.gaestudio.server.rest;

import java.util.List;

import org.junit.Test;

import com.arcbees.gaestudio.companion.domain.Car;

import static org.junit.Assert.assertEquals;

public class KindsResourceIT extends RestIT {
    @Test
    public void createObject_getKinds_KindIsReturned() {
        //given
        Car car = new Car();
        createRemoteObject(car);

        //when
        List<String> kinds = getRemoteKinds();

        //then
        assertEquals(1, kinds.size());
        assertEquals("Car", kinds.get(0));
    }
}
