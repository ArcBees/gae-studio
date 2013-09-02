/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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

    public QueryFilterDto(String property, QueryFilterOperator operator, String value) {
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
