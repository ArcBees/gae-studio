/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto.query;

import java.io.Serializable;

public class QueryFilterDTO implements Serializable {

    private static final long serialVersionUID = 1284195709357989944L;

    private String property;
    private QueryFilterOperatorDTO operator;
    private String value;

    @SuppressWarnings("unused")
    protected QueryFilterDTO() {
    }

    public QueryFilterDTO(String property, QueryFilterOperatorDTO operator, String value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public QueryFilterOperatorDTO getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

}
