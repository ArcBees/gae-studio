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

import java.io.Serializable;

public class EntityDto implements Serializable {
    private static final long serialVersionUID = 353724233366838753L;
    
    private KeyDto key;
    
    private String json;

    @SuppressWarnings("unused")
    protected EntityDto() {
    }

    public EntityDto(KeyDto key, String json) {
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
