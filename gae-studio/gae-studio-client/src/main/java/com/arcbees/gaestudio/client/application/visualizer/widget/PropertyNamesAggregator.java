/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
    PropertyNamesAggregator(
            KeyValuePairBuilder keyValuePairBuilder) {
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
