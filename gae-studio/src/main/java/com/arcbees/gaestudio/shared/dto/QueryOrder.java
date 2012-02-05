/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto;

import java.io.Serializable;

public class QueryOrder implements Serializable {

    private static final long serialVersionUID = 6799347670763722667L;

    private QueryOrderDirection direction;

    private String property;
    
    @SuppressWarnings("unused")
    protected QueryOrder() {
    }
    
    public QueryOrder(QueryOrderDirection direction, String property) {
        this.direction = direction;
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public QueryOrderDirection getDirection() {
        return direction;
    }

}
