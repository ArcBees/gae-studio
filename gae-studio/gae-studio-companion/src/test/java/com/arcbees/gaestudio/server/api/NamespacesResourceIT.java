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

package com.arcbees.gaestudio.server.api;

import org.junit.Test;

import com.jayway.restassured.response.Response;

import static javax.ws.rs.core.Response.Status.OK;

import static org.junit.Assert.assertEquals;

public class NamespacesResourceIT extends RestIT {
    @Test
    public void createObject_getKinds_KindIsReturned() {
        // given
        createRemoteCar();

        // when
        Response response = getRemoteNamespacesResponse();

        // then
        assertEquals(OK.getStatusCode(), response.getStatusCode());
    }
}
