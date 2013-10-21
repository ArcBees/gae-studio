package com.arcbees.gaestudio.server.rest;

import org.junit.Test;

import com.jayway.restassured.response.Response;

import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;

public class NamespacesResourceIT extends RestIT {
    @Test
    public void createObject_getKinds_KindIsReturned() {
        //given
        createRemoteCar();

        //when
        Response response = getRemoteNamespacesResponse();

        //then
        assertEquals(OK.getStatusCode(), response.getStatusCode());
    }
}
