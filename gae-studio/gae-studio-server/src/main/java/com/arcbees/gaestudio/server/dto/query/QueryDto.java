/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dto.query;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class QueryDto implements IsSerializable {
    private String kind;
    private String ancestor;
    private ArrayList<QueryFilterDto> filters;
    private ArrayList<QueryOrderDto> orders;
    private Integer offset;
    private Integer limit;

    @SuppressWarnings("unused")
    protected QueryDto() {
    }

    public QueryDto(String kind, String ancestor, ArrayList<QueryFilterDto> filters, ArrayList<QueryOrderDto> orders,
                    Integer offset, Integer limit) {
        this.kind = kind;
        this.ancestor = ancestor;
        this.filters = filters;
        this.orders = orders;
        this.offset = offset;
        this.limit = limit;
    }

    public String getKind() {
        return kind;
    }

    public String getAncestor() {
        return ancestor;
    }

    public ArrayList<QueryFilterDto> getFilters() {
        return filters;
    }

    public ArrayList<QueryOrderDto> getOrders() {
        return orders;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }
}
