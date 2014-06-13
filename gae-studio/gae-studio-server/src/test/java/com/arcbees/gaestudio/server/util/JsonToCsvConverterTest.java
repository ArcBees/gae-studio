/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonToCsvConverterTest {
    @Test
    public void convertJsonToCsv() throws JSONException, IOException {
        String jsonData = getJsonData();

        String result = JsonToCsvConverter.convert(jsonData);

        assertEquals(getCsvData(), result);
    }

    private String getCsvData() {
        return "id, model, manufacturer, carProperties\n" +
                "23, Civic, Manufacturer(17), CarProperties(19)\n" +
                "24, Accord, Manufacturer(17), CarProperties(20)\n";
    }

    private String getJsonData() throws IOException {
        InputStream stream = getClass().getResourceAsStream("/EasyCars.json");

        return IOUtils.toString(stream);
    }
}
