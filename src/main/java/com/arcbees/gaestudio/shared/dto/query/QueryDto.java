/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto.query;

import java.io.Serializable;
import java.util.ArrayList;

public class QueryDto implements Serializable {

    private static final long serialVersionUID = -7429048102343532748L;

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
