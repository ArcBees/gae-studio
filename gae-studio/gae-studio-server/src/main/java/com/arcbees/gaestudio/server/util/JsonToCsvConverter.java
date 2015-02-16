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

import com.arcbees.gaestudio.shared.PropertyType;

import static com.arcbees.gaestudio.shared.PropertyName.GAE_PROPERTY_TYPE;
import static com.arcbees.gaestudio.shared.PropertyName.ID;
import static com.arcbees.gaestudio.shared.PropertyName.KEY;
import static com.arcbees.gaestudio.shared.PropertyName.KIND;
import static com.arcbees.gaestudio.shared.PropertyName.LATITUDE;
import static com.arcbees.gaestudio.shared.PropertyName.LONGITUDE;
import static com.arcbees.gaestudio.shared.PropertyName.PROPERTY_MAP;
import static com.arcbees.gaestudio.shared.PropertyName.VALUE;

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
            currentObjectPropertyMap = array.getJSONObject(i).getJSONObject(PROPERTY_MAP);
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
        Iterator currentObjectKeys = fullPropertyMap.keys();

        while (currentObjectKeys.hasNext()) {
            columns.addAll(
                    generateColumnNamesFromPropertyMap(String.valueOf(currentObjectKeys.next()), fullPropertyMap));
        }

        return columns;
    }

    private String buildColumnsLine(Set<String> columns) {
        String columnsResult = ID + ", ";
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
            currentId = currentObject.getJSONObject(KEY).get(ID).toString();
            propertyMap = currentObject.getJSONObject(PROPERTY_MAP);

            dataLines += currentId;

            dataLines += generateDataLineFromPropertyMap(propertyMap, fullPropertyMap);
        }

        return dataLines;
    }

    private String writeKeyData(JSONObject currentObjectValue) throws JSONException {
        String currentKind = currentObjectValue.getString(KIND);
        String currentId = currentObjectValue.get(ID).toString();

        return currentKind + "(" + currentId + ")";
    }

    private Set<String> generateColumnNamesFromPropertyMap(String propertyName, JSONObject propertyMap) {
        JSONObject currentPropertyObject = propertyMap.getJSONObject(propertyName);

        return generateColumnNamesFromPropertyType(propertyName, currentPropertyObject);
    }

    private Set<String> generateColumnNamesFromPropertyType(String propertyName, JSONObject currentPropertyObject) {
        Set<String> columns = new LinkedHashSet<>();

        if (currentPropertyObject.has(GAE_PROPERTY_TYPE)) {
            columns.addAll(generateColumns(propertyName, currentPropertyObject));
        } else {
            columns.add(propertyName);
        }

        return columns;
    }

    private Set<String> generateColumns(String propertyName, JSONObject currentPropertyObject) {
        Set<String> columns = new LinkedHashSet<>();
        PropertyType propertyType = PropertyType.valueOf(currentPropertyObject.getString(GAE_PROPERTY_TYPE));

        switch (propertyType) {
            case STRING:
            case KEY:
            case NUMERIC:
            case BOOLEAN:
            case FLOATING:
                columns.add(propertyName);
                break;
            case GEO_PT:
                columns.addAll(generateGeoPtColumnNames(propertyName));
                break;
            case COLLECTION:
                columns.addAll(generateArrayColumnNames(propertyName, currentPropertyObject.getJSONArray(VALUE)));
                break;
        }

        return columns;
    }

    private Set<String> generateArrayColumnNames(String propertyName, JSONArray array) {
        Set<String> arrayColumns = new LinkedHashSet<>();

        for (int i = 0; i < array.length(); i++) {
            arrayColumns.addAll(
                    generateColumnNamesFromPropertyType(propertyName + "[" + i + "]", array.getJSONObject(i)));
        }

        return arrayColumns;
    }

    private Set<String> generateGeoPtColumnNames(String propertyName) {
        Set<String> geoColumns = new LinkedHashSet<>();

        geoColumns.add(propertyName + "." + LATITUDE);
        geoColumns.add(propertyName + "." + LONGITUDE);

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
        return geoPt.get(LATITUDE) + ", " + geoPt.get(LONGITUDE);
    }

    private String writeArrayData(JSONArray array) {
        String arrayData = "";

        for (int i = 0; i < array.length(); i++) {
            arrayData += writePropertyData(array.getJSONObject(i));
            arrayData += ", ";
        }

        return removeLastComma(arrayData);
    }

    private String removeLastComma(String arrayData) {
        return new StringBuilder(arrayData).delete(arrayData.length() - 2, arrayData.length()).toString();
    }

    private String writePropertyData(JSONObject jsonObject) {
        String propertyData = "";

        if (jsonObject.has(GAE_PROPERTY_TYPE)) {
            propertyData += writeData(jsonObject);
        } else {
            propertyData += String.valueOf(jsonObject.get(VALUE));
        }

        return propertyData;
    }

    private String writeData(JSONObject jsonObject) {
        PropertyType propertyType = PropertyType.valueOf(jsonObject.getString(GAE_PROPERTY_TYPE));

        switch (propertyType) {
            case STRING:
            case NUMERIC:
            case BOOLEAN:
            case FLOATING:
                return String.valueOf(jsonObject.get(VALUE));
            case GEO_PT:
                return writeGeoData((JSONObject) jsonObject.get(VALUE));
            case COLLECTION:
                return writeArrayData((JSONArray) jsonObject.get(VALUE));
            case KEY:
                return writeKeyData((JSONObject) jsonObject.get(VALUE));
        }

        return String.valueOf(jsonObject.get(VALUE));
    }
}
