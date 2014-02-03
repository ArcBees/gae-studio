/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

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
