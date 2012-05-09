/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto.query;

import java.io.Serializable;

public class QueryOrderDTO implements Serializable {

    private static final long serialVersionUID = 6799347670763722667L;

    private QueryOrderDirectionDTO direction;

    private String property;
    
    @SuppressWarnings("unused")
    protected QueryOrderDTO() {
    }
    
    public QueryOrderDTO(QueryOrderDirectionDTO direction, String property) {
        this.direction = direction;
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public QueryOrderDirectionDTO getDirection() {
        return direction;
    }

}
