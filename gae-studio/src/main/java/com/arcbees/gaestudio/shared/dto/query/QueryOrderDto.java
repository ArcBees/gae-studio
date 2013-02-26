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

package com.arcbees.gaestudio.shared.dto.query;

import java.io.Serializable;

public class QueryOrderDto implements Serializable {
    private static final long serialVersionUID = 6799347670763722667L;

    private QueryOrderDirectionDto direction;
    private String property;
    
    @SuppressWarnings("unused")
    protected QueryOrderDto() {
    }
    
    public QueryOrderDto(QueryOrderDirectionDto direction, String property) {
        this.direction = direction;
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public QueryOrderDirectionDto getDirection() {
        return direction;
    }
}
