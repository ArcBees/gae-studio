/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class KeyValuePairBuilder {
    public List<KeyValuePair> fromParsedEntity(ParsedEntity parsedEntity) {
        List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();

        Set<String> properties = parsedEntity.propertyKeys();
        List<String> propertiesList = Lists.newArrayList(properties);
        Collections.sort(propertiesList);

        for (String prop : propertiesList) {
            String val = parsedEntity.getProperty(prop).toString();
            val = cleanupValue(val);

            KeyValuePair keyValuePair = new KeyValuePair(prop, val);
            keyValuePairs.add(keyValuePair);
        }

        return keyValuePairs;
    }

    private String cleanupValue(String val) {
        val = val.replace("{\"value\":", "");
        val = removeTrailingCurlyBracket(val);
        return val;
    }

    private String removeTrailingCurlyBracket(String val) {
        boolean lastCharIsCurlyBracket = val.charAt(val.length() - 1) == '}';

        if (lastCharIsCurlyBracket) {
            val = removeLastCharacter(val);
        }

        return val;
    }

    private String removeLastCharacter(String val) {
        val = val.substring(0, val.length() - 1);
        return val;
    }
}
