/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto;

import java.io.Serializable;

public class QueryOrderDTO implements Serializable {

    private static final long serialVersionUID = 6799347670763722667L;

    private OrderDirection direction;

    private String property;
    
    @SuppressWarnings("unused")
    protected QueryOrderDTO() {
    }
    
    public QueryOrderDTO(OrderDirection direction, String property) {
        this.direction = direction;
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public OrderDirection getDirection() {
        return direction;
    }

}
