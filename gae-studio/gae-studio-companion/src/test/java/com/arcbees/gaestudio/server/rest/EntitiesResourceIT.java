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
import static org.junit.Assert.assertEquals;

public class EntitiesResourceIT extends RestIT {
    private final Gson gson = new GsonBuilder().create();
    private final TypeToken<List<Car>> type = new TypeToken<List<Car>>() {
    };

    @Test
    public void createObject_getEntities_entitiesReturned() {
        //given
        Car car = new Car();
        createRemoteObject(car);

        //when
        Response response = getRemoteEntities("Car");

        //then
        List<Car> entities = gson.fromJson(response.asString(), type.getType());
        assertEquals(1, entities.size());
    }

    @Test
    public void createObject_getEntitiesWithNullKind_badRequest() {
        //given
        Car car = new Car();
        createRemoteObject(car);

        //when
        Response response = getRemoteEntitiesWithNullKind();

        //then
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createObject_createEmptyEntity_returnEmptyEntity() {
        //given
        Car car = new Car();
        createRemoteObject(car);

        //when

    }

    private Response getRemoteEntities(String kind) {
        return given().queryParam(TestEndPoints.PARAM_KIND, kind).get(getAbsoluteUri(EndPoints.ENTITIES));
    }

    private Response getRemoteEntitiesWithNullKind() {
        return given().get(getAbsoluteUri(EndPoints.ENTITIES));
    }
}
