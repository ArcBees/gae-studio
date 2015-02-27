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

import com.arcbees.gaestudio.shared.PropertyName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KeyDto {
    private KeyDto parentKey;
    private AppIdNamespaceDto appIdNamespace;
    private String encodedKey;
    private String kind;
    private Long id;
    private String name;

    @JsonCreator
    public KeyDto(
            @JsonProperty(PropertyName.ENCODED_KEY) String encodedKey,
            @JsonProperty(PropertyName.KIND) String kind,
            @JsonProperty(PropertyName.ID) Long id,
            @JsonProperty(PropertyName.NAME) String name,
            @JsonProperty(PropertyName.PARENT_KEY) KeyDto parentKey,
            @JsonProperty(PropertyName.APP_ID_NAMESPACE) AppIdNamespaceDto appIdNamespace) {
        this.encodedKey = encodedKey;
        this.kind = kind;
        this.id = id;
        this.name = name;
        this.parentKey = parentKey;

        this.appIdNamespace = appIdNamespace;
    }

    @SuppressWarnings("unused")
    protected KeyDto() {
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
