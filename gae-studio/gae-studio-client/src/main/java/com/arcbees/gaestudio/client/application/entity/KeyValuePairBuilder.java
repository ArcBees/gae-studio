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

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.util.KeyPrettifier.KeyPrettifier;
import com.arcbees.gaestudio.shared.PropertyType;
import com.google.common.collect.Lists;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

public class KeyValuePairBuilder {
    private final KeyPrettifier keyPrettifier;

    @Inject
    KeyValuePairBuilder(KeyPrettifier keyPrettifier) {
        this.keyPrettifier = keyPrettifier;
    }

    public List<KeyValuePair> fromParsedEntity(ParsedEntity parsedEntity) {
        List<KeyValuePair> keyValuePairs = new ArrayList<>();

        Set<String> properties = parsedEntity.propertyKeys();
        List<String> propertiesList = Lists.newArrayList(properties);
        Collections.sort(propertiesList);

        for (String prop : propertiesList) {
            String val = parsedEntity.getCleanedUpProperty(prop).toString();
            PropertyType type = parsedEntity.getPropertyType(prop);

            if (type == PropertyType.KEY) {
                JSONObject parsedJson = JSONParser.parseStrict(val).isObject();
                val = keyPrettifier.prettifyKey(parsedJson);
            }

            KeyValuePair keyValuePair = new KeyValuePair(prop, val);
            keyValuePairs.add(keyValuePair);
        }

        return keyValuePairs;
    }
}
