/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
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
