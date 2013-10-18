package com.arcbees.gaestudio.server.rest;

import com.arcbees.gaestudio.shared.dto.query.QueryRecordDto;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.jayway.restassured.response.Response;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.junit.Assert.assertEquals;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

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
