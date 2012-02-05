/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto;

import java.io.Serializable;

public class FilterDTO implements Serializable {

    private static final long serialVersionUID = 1284195709357989944L;

    private String property;
    private FilterOperator operator;
    private String value;

    @SuppressWarnings("unused")
    protected FilterDTO() {
    }

    public FilterDTO(String property, FilterOperator operator, String value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public FilterOperator getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

}
