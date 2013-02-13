/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
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
