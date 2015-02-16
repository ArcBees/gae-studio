/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QueryDto implements Serializable {
    private String kind;
    private String ancestor;
    private List<QueryFilterDto> filters;
    private List<QueryOrderDto> orders;
    private Integer offset;
    private Integer limit;

    @SuppressWarnings("unused")
    public QueryDto() {
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

    public List<QueryFilterDto> getFilters() {
        return filters;
    }

    public List<QueryOrderDto> getOrders() {
        return orders;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setAncestor(String ancestor) {
        this.ancestor = ancestor;
    }

    public void setFilters(List<QueryFilterDto> filters) {
        this.filters = filters;
    }

    public void setOrders(List<QueryOrderDto> orders) {
        this.orders = orders;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
