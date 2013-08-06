/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import java.util.Set;

import com.arcbees.gaestudio.client.dto.entity.EntityDto;
import com.arcbees.gaestudio.client.dto.entity.KeyDto;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class ParsedEntity {
    private static final String PROPERTY_MAP = "propertyMap";

    private EntityDto entityDto;
    private JSONObject jsonObject;

    public ParsedEntity(EntityDto entityDto) {
        this.entityDto = EntityDto.create(entityDto.getKey(), entityDto.getJson());

        parseJson();
    }

    public Set<String> propertyKeys() {
        return getPropertyMap().keySet();
    }

    public Boolean hasProperty(String key) {
        return getPropertyMap().containsKey(key);
    }

    public JSONValue getProperty(String key) {
        return getPropertyMap().get(key);
    }

    public JSONObject getPropertyMap() {
        return jsonObject.get(PROPERTY_MAP).isObject();
    }

    public void setEntityDto(EntityDto entityDto) {
        this.entityDto = entityDto;

        parseJson();
    }

    public EntityDto getEntityDto() {
        return entityDto;
    }

    public KeyDto getKey() {
        return entityDto.getKey();
    }

    public String getJson() {
        return entityDto.getJson();
    }

    public void parseJson() {
        jsonObject = JSONParser.parseStrict(getJson()).isObject();
    }
}
