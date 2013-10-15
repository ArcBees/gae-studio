package com.arcbees.gaestudio.server.rest;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDto;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.jayway.restassured.response.Response;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
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

    private List<DbOperationRecordDto> getOperations(Long currentOperationId) {
        Response response = given().queryParam(TestEndPoints.PARAM_LASTID, currentOperationId).get(getAbsoluteUri(EndPoints.OPERATIONS));

        return gson.fromJson(response.asString(), queryType.getType());
    }

    private Long stopRecording() {
        Response response = given().delete(getAbsoluteUri(EndPoints.RECORD));

        return gson.fromJson(response.asString(), Long.class);
    }

    private Long startRecording() {
        Response response = given().post(getAbsoluteUri(EndPoints.RECORD));

        return gson.fromJson(response.asString(), Long.class);
    }
}
