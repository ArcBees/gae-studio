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

package com.arcbees.gaestudio.client.application.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.util.keyprettifier.KeyDtoMapper;
import com.arcbees.gaestudio.client.util.keyprettifier.KeyPrettifier;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.common.collect.Lists;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

public class KeyValuePairBuilder {
    private final KeyPrettifier keyPrettifier;
    private final KeyDtoMapper keyDtoMapper;

    @Inject
    KeyValuePairBuilder(
            KeyPrettifier keyPrettifier,
            KeyDtoMapper keyDtoMapper) {
        this.keyPrettifier = keyPrettifier;
        this.keyDtoMapper = keyDtoMapper;
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
                KeyDto keyDto = keyDtoMapper.fromJSONObject(parsedJson);
                val = keyPrettifier.prettifyKey(keyDto);
            }

            KeyValuePair keyValuePair = new KeyValuePair(prop, val);
            keyValuePairs.add(keyValuePair);
        }

        return keyValuePairs;
    }
}
