/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.entity.KeyValuePair;
import com.arcbees.gaestudio.client.application.entity.KeyValuePairBuilder;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.common.collect.Sets;

public class PropertyNamesAggregator {
    private final KeyValuePairBuilder keyValuePairBuilder;

    @Inject
    PropertyNamesAggregator(KeyValuePairBuilder keyValuePairBuilder) {
        this.keyValuePairBuilder = keyValuePairBuilder;
    }

    public Set<String> aggregatePropertyNames(List<ParsedEntity> entities) {
        Set<String> properties = Sets.newHashSet();

        for (ParsedEntity entity : entities) {
            Set<String> entityProperties = getProperties(entity);
            properties.addAll(entityProperties);
        }

        return properties;
    }

    private Set<String> getProperties(ParsedEntity entity) {
        List<KeyValuePair> keyValuePairs = keyValuePairBuilder.fromParsedEntity(entity);
        Set<String> entityProperties = Sets.newHashSet();

        for (KeyValuePair keyValuePair : keyValuePairs) {
            String key = keyValuePair.getKey();
            entityProperties.add(key);
        }

        return entityProperties;
    }
}
