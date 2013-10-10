package com.arcbees.gaestudio.server.rest;

import java.util.List;

import org.junit.Before;

import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

public abstract class RestIT {
    public static final String HOSTNAME;

    private final Gson gson = new GsonBuilder().create();
    private final TypeToken<List<String>> type = new TypeToken<List<String>>() {
    };

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

    protected Response createRemoteCar() {
        Car car = new Car();
        return createRemoteObject(car);
    }

    protected Response createRemoteObject(Car car) {
        return given().body(car).post(getAbsoluteUri(TestEndPoints.PUT_OBJECT));
    }

    protected List<String> getRemoteKinds() {
        Response response = given().get(getAbsoluteUri(EndPoints.KINDS));

        return gson.fromJson(response.asString(), type.getType());
    }
}
