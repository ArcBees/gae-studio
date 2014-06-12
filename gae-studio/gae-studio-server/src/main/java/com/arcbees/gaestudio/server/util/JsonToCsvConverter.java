/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;

import java.util.*;

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

        for (int i = 0; i < array.length(); i++) {
            Iterator currentObjectKeys = array.getJSONObject(i).getJSONObject("propertyMap").keys();
            while (currentObjectKeys.hasNext()) {
                columns.add(currentObjectKeys.next().toString());
            }
        }

        return columns;
    }

    private static String buildColumnsLine(Set<String> columns) {
        String columnsResult = "";
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

    private static String buildDataLines(JSONArray array, Set<String> columns) throws JSONException {
        //TODO : Build data lines
        return "";
    }
}
