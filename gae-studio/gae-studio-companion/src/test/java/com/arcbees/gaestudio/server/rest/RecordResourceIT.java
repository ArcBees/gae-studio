package com.arcbees.gaestudio.server.rest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RecordResourceIT extends RestIT {
    @Test
    public void record_createOneObject_shouldReturnOneOperationRecorded() {
        //given
        Long lastOperationId = startRecording();

        //when
        createRemoteCar();

        Long currentOperationId = stopRecording();

        //then
        assertEquals(1, currentOperationId - lastOperationId);
    }

    @Test
    public void record_noOperations_shouldHaveNotIncremented() {
        //given
        Long lastOperationId = startRecording();

        //when
        Long operationId = stopRecording();

        //then
        assertEquals(0, operationId - lastOperationId);
    }
}
