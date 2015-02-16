/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EntityDto {
    private KeyDto key;
    private String json;

    @SuppressWarnings("unused")
    protected EntityDto() {
    }

    @JsonCreator
    public EntityDto(@JsonProperty("key") KeyDto key,
            @JsonProperty("json") String json) {
        this.key = key;
        this.json = json;
    }

    public KeyDto getKey() {
        return key;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
