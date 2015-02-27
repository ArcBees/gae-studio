/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.server.util;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

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
        return IOUtils.toString(getClass().getResourceAsStream(path)).replace("\r", "");
    }
}
