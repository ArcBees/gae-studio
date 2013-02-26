/**
 * Copyright 2013 ArcBees Inc.
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
