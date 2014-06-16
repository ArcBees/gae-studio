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
    public static String convert(String jsonData) throws JSONException {
        Set<String> columns;
        JSONArray array = new JSONArray(jsonData);

        columns = extractColumns(array);

        String csvResult = buildColumnsLine(columns);

        csvResult += buildDataLines(array, columns);

        return csvResult;
    }

    private static Set<String> extractColumns(JSONArray array) throws JSONException {
        Set<String> columns = new LinkedHashSet<>();
        Iterator currentObjectKeys;
        JSONObject propertyMap;

        for (int i = 0; i < array.length(); i++) {
            propertyMap = array.getJSONObject(i).getJSONObject("propertyMap");
            currentObjectKeys = propertyMap.keys();
            while (currentObjectKeys.hasNext()) {
                columns.addAll(generateColumnNamesFromPropertyMap(String.valueOf(currentObjectKeys.next()), propertyMap));
            }
        }

        return columns;
    }

    private static String buildColumnsLine(Set<String> columns) {
        String columnsResult = "id, ";
        int counter = 1;

        for (String column : columns) {
            columnsResult += column;
            columnsResult = addSeparator(columns, columnsResult, counter);

            counter++;
        }

        return columnsResult;
    }

    private static String addSeparator(Set<String> columns, String columnsResult, int counter) {
        if (counter < columns.size()) {
            columnsResult += ", ";
        } else {
            columnsResult += "\n";
        }
        return columnsResult;
    }

    private static String buildDataLines(JSONArray dataArray, Set<String> allColumns) throws JSONException {
        JSONObject currentProperties;
        JSONObject currentColumn;
        JSONObject currentObject;
        String dataLines = "";
        String currentId;
        int counter;

        for (int i = 0; i < dataArray.length(); i++) {
            currentObject = dataArray.getJSONObject(i);
            currentId = currentObject.getJSONObject("key").get("id").toString();
            currentProperties = currentObject.getJSONObject("propertyMap");
            counter = 1;

            dataLines += currentId + ", ";

            for (String column : allColumns) {
                if (currentProperties.has(column)) {
                    currentColumn = currentProperties.getJSONObject(column);

                    if (columnIsNotAKey(currentProperties, column)) {
                        dataLines += currentColumn.get("value").toString();
                    } else {
                        dataLines += writeKeyData(currentColumn.getJSONObject("value"));
                    }
                }

                dataLines = addSeparator(allColumns, dataLines, counter);

                counter++;
            }
        }

        return dataLines;
    }

    private static String writeKeyData(JSONObject currentObjectValue) throws JSONException {
        String currentKind = currentObjectValue.getString("kind");
        String currentId = currentObjectValue.get("id").toString();

        return currentKind + "(" + currentId + ")";
    }

    private static boolean columnIsNotAKey(JSONObject currentObject, String column) throws JSONException {
        JSONObject currentColumn = currentObject.getJSONObject(column);

        return !currentColumn.has("__gaePropertyType") || !currentColumn.getString("__gaePropertyType").equals("KEY");
    }

    private static Set<String> generateColumnNamesFromPropertyMap(String propertyName, JSONObject propertyMap) {
        JSONObject currentPropertyObject = propertyMap.getJSONObject(propertyName);

        return generateColumnNamesFromPropertyType(propertyName, currentPropertyObject);
    }

    private static Set<String> generateColumnNamesFromPropertyType(String propertyName, JSONObject currentPropertyObject) {
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
        }

        return columns;
    }

    private static Set<String> generateArrayColumnNames(String propertyName, JSONArray array) {
        Set<String> arrayColumns = new LinkedHashSet<>();

        for (int i = 0; i < array.length(); i++) {
            arrayColumns.addAll(generateColumnNamesFromPropertyType(propertyName + "[" + i + "]", array.getJSONObject(i)));
        }

        return arrayColumns;
    }


    private static Set<String> generateGeoPtColumnNames(String propertyName) {
        Set<String> geoColumns = new LinkedHashSet<>();

        geoColumns.add(propertyName + ".latitude");
        geoColumns.add(propertyName + ".longitude");

        return geoColumns;
    }
}
