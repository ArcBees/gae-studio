/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.google.common.base.Objects;

public class AppIdNamespaceDto {
    private String appId;
    private String namespace;

    @SuppressWarnings("unused")
    protected AppIdNamespaceDto() {
    }

    @JsonCreator
    public AppIdNamespaceDto(@JsonProperty("appId") String appId, @JsonProperty("namespace") String namespace) {
        this.appId = appId;
        this.namespace = namespace;
    }

    public String getAppId() {
        return appId;
    }

    public String getNamespace() {
        return namespace;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(appId, namespace);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AppIdNamespaceDto other = (AppIdNamespaceDto) obj;
        return Objects.equal(this.appId, other.appId) && Objects.equal(this.namespace, other.namespace);
    }
}
