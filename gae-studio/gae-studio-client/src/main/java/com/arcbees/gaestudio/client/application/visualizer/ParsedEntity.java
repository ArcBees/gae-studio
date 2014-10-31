/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import java.util.Set;

import com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import static com.arcbees.gaestudio.shared.PropertyName.PROPERTY_MAP;

public class ParsedEntity {
    private EntityDto entityDto;
    private JSONObject jsonObject;

    public ParsedEntity(EntityDto entityDto) {
        this.entityDto = new EntityDto(entityDto.getKey(), entityDto.getJson());

        parseJson();
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public Set<String> propertyKeys() {
        return getPropertyMap().keySet();
    }

    public PropertyType getPropertyType(String key) {
        JSONValue jsonValue = getProperty(key);
        return PropertyUtil.getPropertyType(jsonValue);
    }

    public JSONValue getProperty(String key) {
        return getPropertyMap().get(key);
    }

    public JSONValue getCleanedUpProperty(String key) {
        JSONValue property = getProperty(key);
        return PropertyUtil.cleanUpMetadata(property);
    }

    public JSONObject getPropertyMap() {
        return jsonObject.get(PROPERTY_MAP).isObject();
    }

    public EntityDto getEntityDto() {
        return entityDto;
    }

    public void setEntityDto(EntityDto entityDto) {
        this.entityDto = entityDto;

        parseJson();
    }

    public KeyDto getKey() {
        return entityDto.getKey();
    }

    public String getJson() {
        return jsonObject.toString();
    }

    public void parseJson() {
        jsonObject = JSONParser.parseStrict(entityDto.getJson()).isObject();
    }
}
