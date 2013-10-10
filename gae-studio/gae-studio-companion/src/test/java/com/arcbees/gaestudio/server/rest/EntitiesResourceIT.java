package com.arcbees.gaestudio.server.rest;

import java.util.List;

import org.junit.Test;

import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EntitiesResourceIT extends RestIT {
    private final Gson gson = new GsonBuilder().create();
    private final TypeToken<List<Car>> type = new TypeToken<List<Car>>() {
    };

    private String CAR_KIND = "Car";
    private String UNEXISTENT_KIND = "UnexistentKind";

    @Test
    public void createObject_getEntities_entitiesReturned() {
        //given
        createRemoteCar();

        //when
        Response response = getRemoteEntities(CAR_KIND);

        //then
        List<Car> entities = gson.fromJson(response.asString(), type.getType());
        assertEquals(1, entities.size());
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
}
