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

import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static org.junit.Assert.assertEquals;

public class OperationsResourceIT extends RestIT {
    private final TypeToken<List<QueryRecordDto>> queryType = new TypeToken<List<QueryRecordDto>>() {
    };

    @Test
    public void record_createOneObject_shouldReturnOneOperation() {
        //given
        Long lastOperationId = startRecording();

        //when
        createRemoteCar();

        stopRecording();

        //then
        assertEquals(1, getOperations(lastOperationId).size());
    }

    @Test
    public void record_createDelete_shouldReturnTwoOperations() {
        //given
        Long lastOperationId = startRecording();

        //when
        Long carId = createRemoteCar();
        deleteRemoteCar(carId);

        stopRecording();

        //then
        assertEquals(2, getOperations(lastOperationId).size());
    }

    @Test
    public void record_createMultipleEntitiesAndQuery_shouldReturnGoodNumberOfOperations() {
        //given
        Long lastOperationId = startRecording();

        //when
        createRemoteCar();
        createRemoteCar();
        Long carId = createRemoteCar();
        getRemoteCar(carId);

        stopRecording();

        //then
        assertEquals(4, getOperations(lastOperationId).size());
    }

    @Test
    public void record_noOperations_returnNoContent() {
        //given
        Long lastOperationId = startRecording();

        //when
        stopRecording();
        Response response = getOperationsResponse(lastOperationId);

        //then
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void getOperations_withNoLastId_returnsBadRequest() {
        //when
        Response response = getOperationsWithNoLastId();

        //then
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    private List<QueryRecordDto> getOperations(Long currentOperationId) {
        Response response = getOperationsResponse(currentOperationId);

        return gson.fromJson(response.asString(), queryType.getType());
    }

    private Response getOperationsResponse(Long currentOperationId) {
        return given().queryParam(TestEndPoints.PARAM_ID, currentOperationId).get(getAbsoluteUri(EndPoints.OPERATIONS));
    }

    private Response getOperationsWithNoLastId() {
        return given().get(getAbsoluteUri(EndPoints.OPERATIONS));
    }
}
