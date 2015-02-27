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

package com.arcbees.gaestudio.shared.dto.query;

import java.io.Serializable;

import com.arcbees.gaestudio.shared.QueryFilterOperator;

public class QueryFilterDto implements Serializable {
    private String property;
    private QueryFilterOperator operator;
    private String value;

    @SuppressWarnings("unused")
    public QueryFilterDto() {
    }

    public QueryFilterDto(
            String property,
            QueryFilterOperator operator,
            String value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public QueryFilterOperator getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setOperator(QueryFilterOperator operator) {
        this.operator = operator;
    }
}
