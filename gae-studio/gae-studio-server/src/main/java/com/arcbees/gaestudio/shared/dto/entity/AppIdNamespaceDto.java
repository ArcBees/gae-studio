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
    public AppIdNamespaceDto(
            @JsonProperty("appId") String appId,
            @JsonProperty("namespace") String namespace) {
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
