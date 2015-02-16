/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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

    public QueryDto(
            String kind,
            String ancestor,
            ArrayList<QueryFilterDto> filters,
            ArrayList<QueryOrderDto> orders,
            Integer offset,
            Integer limit) {
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
