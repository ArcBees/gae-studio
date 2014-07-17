/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.PropertyType;
import com.google.common.collect.Lists;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import static com.google.gwt.query.client.GQuery.console;

public class KeyValuePairBuilder {
    public List<KeyValuePair> fromParsedEntity(ParsedEntity parsedEntity) {
        List<KeyValuePair> keyValuePairs = new ArrayList<>();

        Set<String> properties = parsedEntity.propertyKeys();
        List<String> propertiesList = Lists.newArrayList(properties);
        Collections.sort(propertiesList);

        for (String prop : propertiesList) {
            String val = parsedEntity.getCleanedUpProperty(prop).toString();
            PropertyType type = parsedEntity.getPropertyType(prop);

            if (type == PropertyType.KEY) {
                val = prettifyKeys(val);
            }

            KeyValuePair keyValuePair = new KeyValuePair(prop, val);
            keyValuePairs.add(keyValuePair);
        }

        return keyValuePairs;
    }

    private String prettifyKeys(String val) {
        String prettyValue = val;
        try {
            JSONValue jsonValue = JSONParser.parseStrict(val);
            JSONObject jsonObject = jsonValue.isObject();
            JSONValue kind = jsonObject.get(PropertyName.KIND);
            JSONValue id = jsonObject.get(PropertyName.ID);

            String stringKind = kind.toString();

            prettyValue = stringKind.substring(1, stringKind.length() - 1) + " (" + id + ")";
        } catch (JSONException e) {
            console.log(e);
        }

        return prettyValue;
    }
}
