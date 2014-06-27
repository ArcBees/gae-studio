package com.arcbees.gaestudio.server.util;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class CsvToJsonConverterTest {
    @Inject
    CsvToJsonConverter csvToJsonConverter;

    @Test
    public void convertEasyCsvToJson() throws IOException {
        String csvData = getCsvData();

        String result = csvToJsonConverter.convert(csvData, "Car");

        assertEquals(getJsonData(), result);
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