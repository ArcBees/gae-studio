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

public class KeyDto {
    private KeyDto parentKey;
    private AppIdNamespaceDto appIdNamespace;
    private String encodedKey;
    private String kind;
    private Long id;
    private String name;

    @SuppressWarnings("unused")
    protected KeyDto() {
    }

    @JsonCreator
    public KeyDto(@JsonProperty("encodedKey") String encodedKey,
                  @JsonProperty("kind") String kind,
                  @JsonProperty("id") Long id,
                  @JsonProperty("name") String name,
                  @JsonProperty("parentKey") KeyDto parentKey,
                  @JsonProperty("appIdNamespace") AppIdNamespaceDto appIdNamespace) {
        this.encodedKey = encodedKey;
        this.kind = kind;
        this.id = id;
        this.name = name;
        this.parentKey = parentKey;

        this.appIdNamespace = appIdNamespace;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Long getId() {
        return id;
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

    public KeyDto getParentKey() {
        return parentKey;
    }

    public void setParentKey(KeyDto parentKey) {
        this.parentKey = parentKey;
    }

    public AppIdNamespaceDto getAppIdNamespace() {
        return appIdNamespace;
    }

    public void setAppIdNamespace(AppIdNamespaceDto appIdNamespace) {
        this.appIdNamespace = appIdNamespace;
    }

    public String getEncodedKey() {
        return encodedKey;
    }

    public void setEncodedKey(String encodedKey) {
        this.encodedKey = encodedKey;
    }
}
