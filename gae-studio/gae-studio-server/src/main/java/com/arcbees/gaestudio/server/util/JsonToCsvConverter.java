/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonToCsvConverter {
    public String convert(String jsonData) throws JSONException {
        Set<String> columns;
        JSONArray array = new JSONArray(jsonData);

        JSONObject fullPropertyMap = extractFullPropertyMap(array);

        columns = extractColumns(fullPropertyMap);

        String csvResult = buildColumnsLine(columns);

        csvResult += buildDataLines(array, fullPropertyMap);

        return csvResult;
    }

    private JSONObject extractFullPropertyMap(JSONArray array) {
        JSONObject currentObjectPropertyMap;
        JSONObject fullPropertyMap = new JSONObject();

        for (int i = 0; i < array.length(); i++) {
            currentObjectPropertyMap = array.getJSONObject(i).getJSONObject("propertyMap");
            updateFullPropertyMap(currentObjectPropertyMap, fullPropertyMap);
        }

        return fullPropertyMap;
    }

    private void updateFullPropertyMap(JSONObject currentObjectPropertyMap, JSONObject fullPropertyMap) {
        Iterator currentObjectKeys = currentObjectPropertyMap.keys();

        while (currentObjectKeys.hasNext()) {
            String propertyName = String.valueOf(currentObjectKeys.next());
            JSONObject currentObject = currentObjectPropertyMap.getJSONObject(propertyName);
            if (!fullPropertyMap.has(propertyName)) {
                fullPropertyMap.accumulate(propertyName, currentObject);
            }
        }
    }

    private Set<String> extractColumns(JSONObject fullPropertyMap) throws JSONException {
        Set<String> columns = new LinkedHashSet<>();
        Iterator currentObjectKeys;

        currentObjectKeys = fullPropertyMap.keys();

        while (currentObjectKeys.hasNext()) {
            columns.addAll(generateColumnNamesFromPropertyMap(String.valueOf(currentObjectKeys.next()), fullPropertyMap));
        }

        return columns;
    }

    private String buildColumnsLine(Set<String> columns) {
        String columnsResult = "id, ";
        int counter = 1;

        for (String column : columns) {
            columnsResult += column;
            columnsResult = addSeparator(columns, columnsResult, counter);

            counter++;
        }

        return columnsResult;
    }

    private String addSeparator(Set<String> columns, String columnsResult, int counter) {
        if (counter < columns.size()) {
            columnsResult += ", ";
        } else {
            columnsResult += "\n";
        }
        return columnsResult;
    }

    private String buildDataLines(JSONArray dataArray, JSONObject fullPropertyMap) throws JSONException {
        JSONObject propertyMap;
        JSONObject currentObject;
        String dataLines = "";
        String currentId;

        for (int i = 0; i < dataArray.length(); i++) {
            currentObject = dataArray.getJSONObject(i);
            currentId = currentObject.getJSONObject("key").get("id").toString();
            propertyMap = currentObject.getJSONObject("propertyMap");

            dataLines += currentId;

            dataLines += generateDataLineFromPropertyMap(propertyMap, fullPropertyMap);
        }

        return dataLines;
    }

    private String writeKeyData(JSONObject currentObjectValue) throws JSONException {
        String currentKind = currentObjectValue.getString("kind");
        String currentId = currentObjectValue.get("id").toString();

        return currentKind + "(" + currentId + ")";
    }

    private Set<String> generateColumnNamesFromPropertyMap(String propertyName, JSONObject propertyMap) {
        JSONObject currentPropertyObject = propertyMap.getJSONObject(propertyName);

        return generateColumnNamesFromPropertyType(propertyName, currentPropertyObject);
    }

    private Set<String> generateColumnNamesFromPropertyType(String propertyName, JSONObject currentPropertyObject) {
        Set<String> columns = new LinkedHashSet<>();

        if (currentPropertyObject.has("__gaePropertyType")) {
            switch (currentPropertyObject.getString("__gaePropertyType")) {
                case "STRING":
                case "KEY":
                case "NUMERIC":
                case "BOOLEAN":
                case "FLOATING":
                    columns.add(propertyName);
                    break;
                case "GEO_PT":
                    columns.addAll(generateGeoPtColumnNames(propertyName));
                    break;
                case "COLLECTION":
                    columns.addAll(generateArrayColumnNames(propertyName, currentPropertyObject.getJSONArray("value")));
                    break;
            }
        } else {
            columns.add(propertyName);
        }


        return columns;
    }

    private Set<String> generateArrayColumnNames(String propertyName, JSONArray array) {
        Set<String> arrayColumns = new LinkedHashSet<>();

        for (int i = 0; i < array.length(); i++) {
            arrayColumns.addAll(generateColumnNamesFromPropertyType(propertyName + "[" + i + "]", array.getJSONObject(i)));
        }

        return arrayColumns;
    }


    private Set<String> generateGeoPtColumnNames(String propertyName) {
        Set<String> geoColumns = new LinkedHashSet<>();

        geoColumns.add(propertyName + ".latitude");
        geoColumns.add(propertyName + ".longitude");

        return geoColumns;
    }

    private String generateDataLineFromPropertyMap(JSONObject propertyMap, JSONObject fullPropertyMap) {
        Iterator fullPropertyMapKeys = fullPropertyMap.keys();
        String dataLine = "";

        while (fullPropertyMapKeys.hasNext()) {
            dataLine += ", ";
            String propertyName = String.valueOf(fullPropertyMapKeys.next());
            dataLine += writePropertyData(propertyName, propertyMap);
        }

        return dataLine + "\n";
    }

    private String writePropertyData(String propertyName, JSONObject propertyMap) {
        String propertyData = "";

        if (propertyMap.has(propertyName)) {
            JSONObject currentPropertyObject = propertyMap.getJSONObject(propertyName);

            propertyData += writePropertyData(currentPropertyObject);
        }

        return propertyData;
    }

    private String writeGeoData(JSONObject geoPt) {
        return geoPt.get("latitude") + ", " + geoPt.get("longitude");
    }

    private String writeArrayData(JSONArray array) {
        String arrayData = "";

        for (int i = 0; i < array.length(); i++) {
            arrayData += writePropertyData(array.getJSONObject(i));
            if (i < array.length() - 1) {
                arrayData += ", ";
            }
        }

        return arrayData;
    }

    private String writePropertyData(JSONObject jsonObject) {
        String propertyData = "";

        if (jsonObject.has("__gaePropertyType")) {
            switch (jsonObject.getString("__gaePropertyType")) {
                case "STRING":
                case "NUMERIC":
                case "BOOLEAN":
                case "FLOATING":
                    propertyData += String.valueOf(jsonObject.get("value"));
                    break;
                case "GEO_PT":
                    propertyData += writeGeoData((JSONObject) jsonObject.get("value"));
                    break;
                case "COLLECTION":
                    propertyData += writeArrayData((JSONArray) jsonObject.get("value"));
                    break;
                case "KEY":
                    propertyData += writeKeyData((JSONObject) jsonObject.get("value"));
                    break;
            }
        } else {
            propertyData += String.valueOf(jsonObject.get("value"));
        }

        return propertyData;
    }
}
