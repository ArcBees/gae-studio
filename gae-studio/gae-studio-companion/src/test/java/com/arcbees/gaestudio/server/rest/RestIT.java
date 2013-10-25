package com.arcbees.gaestudio.server.rest;

import org.junit.Before;

import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

public abstract class RestIT {
    public static final String HOSTNAME;

    protected final Gson gson = new GsonBuilder().create();
    protected String CAR_KIND = "Car";

    static {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestContentType(ContentType.JSON);

        HOSTNAME = "http://localhost:8888/";
    }

    @Before
    public void setUp() {
        get(getAbsoluteUri(TestEndPoints.CLEAR));
    }

    protected String getAbsoluteUri(String relativeLocation) {
        return HOSTNAME + TestEndPoints.ROOT + relativeLocation;
    }

    protected Long createRemoteCar() {
        Car car = new Car();
        Response response = createRemoteCar(car);

        return gson.fromJson(response.asString(), Long.class);
    }

    protected Response createRemoteCar(Car car) {
        return given().body(car).post(getAbsoluteUri(TestEndPoints.CAR));
    }

    protected Response deleteRemoteCar(Long id) {
        return given().body(id).delete(getAbsoluteUri(TestEndPoints.CAR));
    }

    protected Response getRemoteKindsResponse() {
        return given().get(getAbsoluteUri(EndPoints.KINDS));
    }

    protected Response getRemoteNamespacesResponse() {
        return given().get(getAbsoluteUri(EndPoints.NAMESPACES));
    }

    protected Response deleteAllRemoteEntities() {
        return given().queryParam(TestEndPoints.PARAM_TYPE, DeleteEntities.ALL).delete(getAbsoluteUri(EndPoints.ENTITIES));
    }

    protected Car getRemoteCar(Long id) {
        Response response = given().queryParam(TestEndPoints.PARAM_ID, id).get(getAbsoluteUri(TestEndPoints.CAR));

        return gson.fromJson(response.asString(), Car.class);
    }

    protected Long stopRecording() {
        Response response = given().delete(getAbsoluteUri(EndPoints.RECORD));

        return gson.fromJson(response.asString(), Long.class);
    }

    protected Long startRecording() {
        Response response = given().post(getAbsoluteUri(EndPoints.RECORD));

        return gson.fromJson(response.asString(), Long.class);
    }
}
