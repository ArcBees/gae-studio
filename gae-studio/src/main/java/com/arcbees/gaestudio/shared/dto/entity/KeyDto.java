/**
 * Copyright 2013 ArcBees Inc.
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

import com.google.gwt.user.client.rpc.IsSerializable;

public class KeyDto implements IsSerializable {
    private String kind;
    private Long id;
    private ParentKeyDto parentKeyDTO;

    @SuppressWarnings("unused")
    protected KeyDto() {
    }

    public KeyDto(String kind, Long id, ParentKeyDto parentKeyDTO) {
        this.kind = kind;
        this.id = id;
        this.parentKeyDTO = parentKeyDTO;
    }

    public String getKind() {
        return kind;
    }

    public Long getId() {
        return id;
    }

    public ParentKeyDto getParentKey() {
        return parentKeyDTO;
    }

    public void setParentKey(ParentKeyDto parentKeyDTO) {
        this.parentKeyDTO = parentKeyDTO;
    }
}
