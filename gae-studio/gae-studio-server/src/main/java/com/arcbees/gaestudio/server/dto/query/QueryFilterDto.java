/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dto.query;

import com.arcbees.gaestudio.shared.QueryFilterOperator;
import com.google.gwt.user.client.rpc.IsSerializable;

public class QueryFilterDto implements IsSerializable {
    private String property;
    private QueryFilterOperator operator;
    private String value;

    @SuppressWarnings("unused")
    protected QueryFilterDto() {
    }

    public QueryFilterDto(String property, QueryFilterOperator operator, String value) {
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
