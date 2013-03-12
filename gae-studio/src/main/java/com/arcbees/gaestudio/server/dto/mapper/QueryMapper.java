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

package com.arcbees.gaestudio.server.dto.mapper;

import java.util.ArrayList;

import com.arcbees.gaestudio.shared.dto.query.QueryDto;
import com.arcbees.gaestudio.shared.dto.query.QueryFilterDto;
import com.arcbees.gaestudio.shared.dto.query.QueryFilterOperatorDto;
import com.arcbees.gaestudio.shared.dto.query.QueryOrderDirectionDto;
import com.arcbees.gaestudio.shared.dto.query.QueryOrderDto;
import com.google.apphosting.api.DatastorePb;
import com.google.storage.onestore.v3.OnestoreEntity;

public class QueryMapper {
    @SuppressWarnings("unused")
    private QueryMapper() {
    }

    public static QueryDto mapDTO(DatastorePb.Query query) {
        String kind = query.hasKind() ? query.getKind() : null;
        String ancestor = query.hasAncestor() ? query.getAncestor().toString() : null;

        final ArrayList<QueryFilterDto> filterList = new ArrayList<QueryFilterDto>();
        for (DatastorePb.Query.Filter filter : query.filters()) {
            // TODO find out why this is an array
            OnestoreEntity.Property property = filter.getProperty(0);
            String propertyName = property.getName();
            QueryFilterOperatorDto operator = convertFilterOperator(filter.getOpEnum());
            String value = valueToString(property.getValue());

            filterList.add(new QueryFilterDto(propertyName, operator, value));
        }

        final ArrayList<QueryOrderDto> orderList = new ArrayList<QueryOrderDto>();
        for (DatastorePb.Query.Order order : query.orders()) {
            QueryOrderDirectionDto direction = convertOrderDirection(order.getDirectionEnum());
            String property = order.getProperty();

            orderList.add(new QueryOrderDto(direction, property));
        }

        Integer offset = query.hasOffset() ? query.getOffset() : null;
        Integer limit = query.hasLimit() ? query.getLimit() : null;

        return new QueryDto(kind, ancestor, filterList, orderList, offset, limit);
    }

    private static QueryFilterOperatorDto convertFilterOperator(DatastorePb.Query.Filter.Operator operator) {
        switch (operator) {
        case EQUAL:
            return QueryFilterOperatorDto.EQUAL;

        case GREATER_THAN:
            return QueryFilterOperatorDto.GREATER_THAN;

        case GREATER_THAN_OR_EQUAL:
            return QueryFilterOperatorDto.GREATER_THAN_OR_EQUAL;

        case LESS_THAN:
            return QueryFilterOperatorDto.LESS_THAN;

        case LESS_THAN_OR_EQUAL:
            return QueryFilterOperatorDto.LESS_THAN_OR_EQUAL;

        default:
            throw new IllegalArgumentException("Unknown query filter operator: " + operator);
        }
    }

    private static QueryOrderDirectionDto convertOrderDirection(DatastorePb.Query.Order.Direction direction) {
        switch (direction) {
        case ASCENDING:
            return QueryOrderDirectionDto.ASCENDING;

        case DESCENDING:
            return QueryOrderDirectionDto.DESCENDING;

        default:
            throw new IllegalArgumentException("Unknown query order direction: " + direction);
        }
    }

    // TODO find out if there's a built-in way to do this
    private static String valueToString(OnestoreEntity.PropertyValue value) {
        if (value.hasBooleanValue()) {
            return Boolean.toString(value.isBooleanValue());
        } else if (value.hasDoubleValue()) {
            return Double.toString(value.getDoubleValue());
        } else if (value.hasInt64Value()) {
            return Long.toString(value.getInt64Value());
        } else if (value.hasStringValue()) {
            return "\"" + value.getStringValue() + "\"";
        } else {
            throw new IllegalArgumentException("Unknown property value type: " + value.toString());
        }
    }
}
