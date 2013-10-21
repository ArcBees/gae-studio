package com.arcbees.gaestudio.server.rest;

import org.junit.Test;

import com.jayway.restassured.response.Response;

import static org.junit.Assert.assertEquals;
import static javax.ws.rs.core.Response.Status.OK;

public class KindsResourceIT extends RestIT {
    @Test
    public void createObject_getKinds_KindIsReturned() {
        //given
        createRemoteCar();

        //when
        Response response = getRemoteKindsResponse();

        //then
        assertEquals(OK.getStatusCode(), response.getStatusCode());
    }
}
