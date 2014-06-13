/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import java.util.HashSet;
import java.util.Iterator;
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
        Set<String> columns = new HashSet<>();
        Iterator currentObjectKeys;

        for (int i = 0; i < array.length(); i++) {
            currentObjectKeys = array.getJSONObject(i).getJSONObject("propertyMap").keys();
            while (currentObjectKeys.hasNext()) {
                columns.add(currentObjectKeys.next().toString());
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
                        dataLines += currentColumn.getString("value");
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
}
