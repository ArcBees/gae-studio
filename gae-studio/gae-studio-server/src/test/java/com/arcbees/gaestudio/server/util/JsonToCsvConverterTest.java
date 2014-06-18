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

import com.google.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class JsonToCsvConverterTest {
    @Inject
    JsonToCsvConverter jsonToCsvConverter;

    @Test
    public void convertEasyJsonToCsv() throws JSONException, IOException {
        String jsonData = getJsonData();

        String result = jsonToCsvConverter.convert(jsonData);

        assertEquals(getCsvData(), result);
    }

    @Test
    public void convertCollectionsJsonToCsv() throws JSONException, IOException {
        String jsonData = getCollectionsJsonData();

        String result = jsonToCsvConverter.convert(jsonData);

        assertEquals(getCollectionsCsvData(), result);
    }

    private String getCsvData() {
        return "id, model, manufacturer, carProperties\n" +
                "23, Civic, Manufacturer(17), CarProperties(19)\n" +
                "24, Accord, Manufacturer(17), CarProperties(20)\n";
    }

    private String getJsonData() throws IOException {
        return getFileContent("/EasyCars.json");
    }

    private String getCollectionsCsvData() throws IOException {
        return getFileContent("/Car.csv");
    }

    private String getCollectionsJsonData() throws IOException {
        return getFileContent("/Car.json");
    }

    private String getFileContent(String path) throws IOException {
        return IOUtils.toString(getClass().getResourceAsStream(path));
    }
}
