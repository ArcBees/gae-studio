/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.Set;

import org.junit.Before;

import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.domain.StringIdEntity;
import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;

public class RestIT {
    public static final String HOSTNAME;

    protected static final String CAR_KIND = "Car";
    protected static final String STRING_ENTITY_KIND = "StringIdEntity";
    protected static final String AN_ENTITY_NAME = "An entity name";

    static {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestContentType(ContentType.JSON);

        HOSTNAME = "http://localhost:8888/";
    }

    protected final Gson gson = new GsonBuilder().create();

    @Before
    public void setUp() {
        clearDatabase();
    }

    public void clearDatabase() {
        get(getAbsoluteUri(TestEndPoints.CLEAR));
    }

    public Car getRemoteCar(Long id) {
        Response response = given().queryParam(TestEndPoints.PARAM_ID, id).get(getAbsoluteUri(TestEndPoints.CAR));

        return gson.fromJson(response.asString(), Car.class);
    }

    public Long createRemoteCar() {
        return createRemoteCar(new Car());
    }

    public Long createRemoteCar(Car car) {
        return given().body(car).post(getAbsoluteUri(TestEndPoints.CAR)).as(Long.class);
    }

    public Response getRemoteCarResponseCode(Long id) {
        return given().queryParam(TestEndPoints.PARAM_ID, id).get(getAbsoluteUri(TestEndPoints.CAR));
    }

    public Response createStringIdEntity(String name) {
        StringIdEntity stringIdEntity = new StringIdEntity();

        stringIdEntity.setName(name);

        return createStringIdEntity(stringIdEntity);
    }

    public Response createStringIdEntity(StringIdEntity stringIdEntity) {
        return given().body(stringIdEntity).post(getAbsoluteUri(TestEndPoints.STRING_ID_ENTITY));
    }

    protected String getAbsoluteUri(String relativeLocation) {
        return HOSTNAME + TestEndPoints.ROOT + relativeLocation;
    }

    protected Set<String> getRemoteKindsResponse() {
        Response response = given().get(getAbsoluteUri(EndPoints.KINDS));
        String[] kinds = response.as(String[].class);

        return Sets.newHashSet(kinds);
    }

    protected Response getRemoteNamespacesResponse() {
        return given().get(getAbsoluteUri(EndPoints.NAMESPACES));
    }

    protected Response deleteAllRemoteEntities() {
        return given().queryParam(TestEndPoints.PARAM_TYPE, DeleteEntities.ALL).delete(getAbsoluteUri(EndPoints
                .ENTITIES));
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
