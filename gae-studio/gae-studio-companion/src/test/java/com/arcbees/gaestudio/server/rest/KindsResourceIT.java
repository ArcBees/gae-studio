/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.rest;

import java.util.Set;

import org.junit.Test;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

public class KindsResourceIT extends RestIT {
    @Test
    public void createObject_getKinds_KindIsReturned() {
        //given
        createRemoteCar();

        //when
        Set<String> response = getRemoteKindsResponse();

        //then
        assertThat(response, hasItem("Car"));
    }
}
