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

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

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
            if (counter < columns.size()) {
                columnsResult += ", ";
            } else {
                columnsResult += "\n";
            }

            counter++;
        }

        return columnsResult;
    }

    private static String buildDataLines(JSONArray dataArray, Set<String> allColumns) throws JSONException {
        JSONObject currentObject;
        String dataLines = "";
        String currentId;
        JSONObject currentColumn;
        int counter;

        for (int i = 0; i < dataArray.length(); i++) {
            currentId = dataArray.getJSONObject(i).getJSONObject("key").getString("id");
            currentObject = dataArray.getJSONObject(i).getJSONObject("propertyMap");
            counter = 1;

            dataLines += currentId + ", ";

            for (String column : allColumns) {
                if (currentObject.has(column)) {
                    currentColumn = currentObject.getJSONObject(column);

                    if (columnIsNotAKey(currentObject, column)) {
                        dataLines += currentColumn.getString("value");
                    } else {
                        dataLines += writeKeyData(currentColumn.getJSONObject("value"));
                    }
                }

                if (counter < allColumns.size()) {
                    dataLines += ", ";
                } else {
                    dataLines += "\n";
                }

                counter++;
            }
        }

        return dataLines;
    }

    private static String writeKeyData(JSONObject currentObjectValue) throws JSONException {
        String currentKind = currentObjectValue.getString("kind");
        String currentId = currentObjectValue.getString("id");

        return currentKind + "(" + currentId + ")";
    }

    private static boolean columnIsNotAKey(JSONObject currentObject, String column) throws JSONException {
        JSONObject currentColumn = currentObject.getJSONObject(column);

        return !currentColumn.has("__gaePropertyType") || !currentColumn.getString("__gaePropertyType").equals("KEY");
    }
}
