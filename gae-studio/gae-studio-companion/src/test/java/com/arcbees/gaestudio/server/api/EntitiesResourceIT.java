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

import java.util.List;

import org.junit.Test;

import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.domain.StringIdEntity;
import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.response.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static com.jayway.restassured.RestAssured.given;

public class EntitiesResourceIT extends RestIT {
    private static final String UNEXISTENT_KIND = "UnexistentKind";

    private final TypeToken<List<Car>> carListType = new TypeToken<List<Car>>() {
    };
    private final TypeToken<List<StringIdEntity>> stringIdEntityListType = new TypeToken<List<StringIdEntity>>() {
    };

    @Test
    public void getEntities_createObject_entityReturned() {
        // given
        createRemoteCar();

        // when
        Response response = getRemoteEntities(CAR_KIND);

        // then
        List<Car> entities = gson.fromJson(response.asString(), carListType.getType());
        assertEquals(1, entities.size());
        assertEquals(OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void getEntities_createStringIdObject_entityReturned() {
        // given
        createStringIdEntity(AN_ENTITY_NAME);

        // when
        Response response = getRemoteEntities(STRING_ENTITY_KIND);

        // then
        List<StringIdEntity> entities = gson.fromJson(response.asString(), stringIdEntityListType.getType());
        assertEquals(1, entities.size());
        assertEquals(OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void getEntitiesWithNoKind_createObject_badRequest() {
        // given
        createRemoteCar();

        // when
        Response response = getRemoteEntitiesWithNoKind();

        // then
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createEmptyEntity_createObject_returnEmptyEntity() {
        // given
        createRemoteCar();

        // when
        Response response = createRemoteEmptyEntity(CAR_KIND);

        // then
        Car emptyCar = gson.fromJson(response.asString(), Car.class);
        assertNotNull(emptyCar);
        assertEquals(OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void postEntitiesWithNoKind_createObject_badRequest() {
        // given
        createRemoteCar();

        // when
        Response response = postRemoteEntitiesWithNoKind();

        // then
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void postEntitiesUnexistentEntity_createObject_notFound() {
        // given
        createRemoteCar();

        // when
        Response response = createRemoteEmptyEntity(UNEXISTENT_KIND);

        // then
        assertEquals(NOT_FOUND.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void deleteEntities_createObject_noContent() {
        // given
        createRemoteCar();

        // when
        Response response = deleteAllRemoteEntities();

        // then
        Response getEntitiesResponse = getRemoteEntities(CAR_KIND);
        List<Car> entities = gson.fromJson(getEntitiesResponse.asString(), carListType.getType());
        assertEquals(0, entities.size());
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void deleteEntitiesNoDeleteType_createObject_badRequest() {
        // given
        createRemoteCar();

        // when
        Response response = deleteRemoteEntitiesWithNoDeleteType();

        // then
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void getCount_createTwoObjects_OkWithTwo() {
        // given
        createRemoteCar();
        createRemoteCar();

        // when
        Response response = getCount(CAR_KIND);

        // then
        assertEquals(2L, (long) gson.fromJson(response.asString(), Long.class));
        assertEquals(OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void getCountWithNoKind_createTwoObjects_badRequest() {
        // given
        createRemoteCar();
        createRemoteCar();

        // when
        Response response = getCountWithNoKind();

        // then
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    private Response createRemoteEmptyEntity(String kind) {
        return given().queryParam(TestEndPoints.PARAM_KIND, kind).post(getAbsoluteUri(EndPoints.ENTITIES));
    }

    private Response getRemoteEntities(String kind) {
        return given().queryParam(TestEndPoints.PARAM_KIND, kind).get(getAbsoluteUri(EndPoints.ENTITIES));
    }

    private Response getRemoteEntitiesWithNoKind() {
        return given().get(getAbsoluteUri(EndPoints.ENTITIES));
    }

    private Response postRemoteEntitiesWithNoKind() {
        return given().post(getAbsoluteUri(EndPoints.ENTITIES));
    }

    private Response deleteRemoteEntitiesWithNoDeleteType() {
        return given().delete(getAbsoluteUri(EndPoints.ENTITIES));
    }

    private Response getCount(String kind) {
        return given().queryParam(TestEndPoints.PARAM_KIND, kind).get(
                getAbsoluteUri(EndPoints.ENTITIES + EndPoints.COUNT));
    }

    private Response getCountWithNoKind() {
        return given().get(getAbsoluteUri(EndPoints.ENTITIES + EndPoints.COUNT));
    }
}
