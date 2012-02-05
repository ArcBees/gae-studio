/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto;

import java.io.Serializable;

public class QueryFilter implements Serializable {

    private static final long serialVersionUID = 1284195709357989944L;

    private String property;
    private QueryFilterOperator operator;
    private String value;

    @SuppressWarnings("unused")
    protected QueryFilter() {
    }

    public QueryFilter(String property, QueryFilterOperator operator, String value) {
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

}
