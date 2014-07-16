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

public class ParentKeyDto {
    private String encodedKey;
    private String kind;
    private Long id;
    private String name;

    @SuppressWarnings("unused")
    protected ParentKeyDto() {
    }

    @JsonCreator
    public ParentKeyDto(@JsonProperty("encodedKey") String encodedKey,
                        @JsonProperty("kind") String kind,
                        @JsonProperty("id") Long id,
                        @JsonProperty("name") String name) {
        this.encodedKey = encodedKey;
        this.kind = kind;
        this.id = id;
        this.name = name;
    }

    public ParentKeyDto(String encodedKey,
                        String kind,
                        Long id) {
        this.encodedKey = encodedKey;
        this.kind = kind;
        this.id = id;
        this.name = "";
    }

    public ParentKeyDto(String encodedKey,
                        String kind,
                        String name) {
        this.encodedKey = encodedKey;
        this.kind = kind;
        this.id = 0l;
        this.name = name;
    }

    public String getEncodedKey() {
        return encodedKey;
    }

    public String getKind() {
        return kind;
    }

    public Long getId() {
        return id;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
