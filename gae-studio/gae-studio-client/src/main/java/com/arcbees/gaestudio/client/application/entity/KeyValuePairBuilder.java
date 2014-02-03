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
import com.google.common.collect.Lists;

public class KeyValuePairBuilder {
    public List<KeyValuePair> fromParsedEntity(ParsedEntity parsedEntity) {
        List<KeyValuePair> keyValuePairs = new ArrayList<KeyValuePair>();

        Set<String> properties = parsedEntity.propertyKeys();
        List<String> propertiesList = Lists.newArrayList(properties);
        Collections.sort(propertiesList);

        for (String prop : propertiesList) {
            String val = parsedEntity.getCleanedUpProperty(prop).toString();

            KeyValuePair keyValuePair = new KeyValuePair(prop, val);
            keyValuePairs.add(keyValuePair);
        }

        return keyValuePairs;
    }
}
