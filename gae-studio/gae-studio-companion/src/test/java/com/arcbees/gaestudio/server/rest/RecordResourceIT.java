/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

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
