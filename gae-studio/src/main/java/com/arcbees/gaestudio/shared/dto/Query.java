/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class Query implements Serializable {

    private static final long serialVersionUID = -7429048102343532748L;

    private String kind;
    private String ancestor;
    private ArrayList<QueryFilter> filters;
    private ArrayList<QueryOrder> orders;
    private Integer offset;
    private Integer limit;
    
    @SuppressWarnings("unused")
    protected Query() {
    }
    
    public Query(String kind, String ancestor, ArrayList<QueryFilter> filters, ArrayList<QueryOrder> orders,
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

    public ArrayList<QueryFilter> getFilters() {
        return filters;
    }

    public ArrayList<QueryOrder> getOrders() {
        return orders;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }

}
