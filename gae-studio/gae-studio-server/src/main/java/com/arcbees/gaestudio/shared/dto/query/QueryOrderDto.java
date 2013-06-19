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
