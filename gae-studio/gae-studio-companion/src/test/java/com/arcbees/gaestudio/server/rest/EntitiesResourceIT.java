/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.List;

import org.junit.Test;

import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EntitiesResourceIT extends RestIT {
    private final TypeToken<List<Car>> carListType = new TypeToken<List<Car>>() {
    };

    private String UNEXISTENT_KIND = "UnexistentKind";

    @Test
    public void createObject_getEntities_entitiesReturned() {
        //given
        Car car = new Car();
        car.setMake("hello");
        createRemoteCar(car);

        //when
        Response response = getRemoteEntities(CAR_KIND);

        //then
        List<Car> entities = gson.fromJson(response.asString(), carListType.getType());
        assertEquals(1, entities.size());
        assertEquals(OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createObject_getEntitiesWithNoKind_badRequest() {
        //given
        createRemoteCar();

        //when
        Response response = getRemoteEntitiesWithNoKind();

        //then
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createObject_createEmptyEntity_returnEmptyEntity() {
        //given
        createRemoteCar();

        //when
        Response response = createRemoteEmptyEntity(CAR_KIND);

        //then
        Car emptyCar = gson.fromJson(response.asString(), Car.class);
        assertNotNull(emptyCar);
        assertEquals(OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createObject_postEntitiesWithNoKind_badRequest() {
        //given
        createRemoteCar();

        //when
        Response response = postRemoteEntitiesWithNoKind();

        //then
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createObject_postEntitiesUnexistentEntity_notFound() {
        //given
        createRemoteCar();

        //when
        Response response = createRemoteEmptyEntity(UNEXISTENT_KIND);

        //then
        assertEquals(NOT_FOUND.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createObject_deleteEntities_noContent() {
        //given
        createRemoteCar();

        //when
        Response response = deleteAllRemoteEntities();

        //then
        Response getEntitiesResponse = getRemoteEntities(CAR_KIND);
        List<Car> entities = gson.fromJson(getEntitiesResponse.asString(), carListType.getType());
        assertEquals(0, entities.size());
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createObject_deleteEntitiesNoDeleteType_badRequest() {
        //given
        createRemoteCar();

        //when
        Response response = deleteRemoteEntitiesWithNoDeleteType();

        //then
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createTwoObjects_getCount_OkWithTwo() {
        //given
        createRemoteCar();
        createRemoteCar();

        //when
        Response response = getCount(CAR_KIND);

        //then
        assertEquals(2l, (long) gson.fromJson(response.asString(), Long.class));
        assertEquals(OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createTwoObjects_getCountWithNoKind_badRequest() {
        //given
        createRemoteCar();
        createRemoteCar();

        //when
        Response response = getCountWithNoKind();

        //then
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
        return given().queryParam(TestEndPoints.PARAM_KIND, kind).get(getAbsoluteUri(EndPoints.ENTITIES + EndPoints.COUNT));
    }

    private Response getCountWithNoKind() {
        return given().get(getAbsoluteUri(EndPoints.ENTITIES + EndPoints.COUNT));
    }
}
