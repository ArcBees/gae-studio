package com.arcbees.gaestudio.server.rest;

import org.junit.Test;

import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class OperationsResourceIT extends RestIT {
    @Test
    public void startRecording_createObject_stopRecording() {
        //given
        startRecording();

        //when
        createRemoteCar();

        //then
        stopRecording();
        assertEquals(1, getOperations(0));
    }

    private int getOperations(long id) {
        Response response = given().queryParam(TestEndPoints.PARAM_LASTID, id).get(getAbsoluteUri(EndPoints.OPERATIONS));

        return gson.fromJson(response.asString(), int.class);
    }

    private void stopRecording() {
        given().delete(getAbsoluteUri(EndPoints.RECORD));
    }

    private void startRecording() {
        given().post(getAbsoluteUri(EndPoints.RECORD));
    }
}
