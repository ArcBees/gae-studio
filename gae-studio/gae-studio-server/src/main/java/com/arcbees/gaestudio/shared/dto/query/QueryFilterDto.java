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

public class QueryFilterDto implements Serializable {
    private static final long serialVersionUID = 1284195709357989944L;

    private String property;
    private QueryFilterOperatorDto operator;
    private String value;

    @SuppressWarnings("unused")
    protected QueryFilterDto() {
    }

    public QueryFilterDto(String property, QueryFilterOperatorDto operator, String value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public QueryFilterOperatorDto getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }
}
