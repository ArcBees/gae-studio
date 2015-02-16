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

package com.arcbees.gaestudio.server.dto.mapper;

import java.util.ArrayList;

import com.arcbees.gaestudio.shared.QueryFilterOperator;
import com.arcbees.gaestudio.shared.QueryOrderDirection;
import com.arcbees.gaestudio.shared.dto.query.QueryDto;
import com.arcbees.gaestudio.shared.dto.query.QueryFilterDto;
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
            OnestoreEntity.Property property = filter.getProperty(0);
            String propertyName = property.getName();
            QueryFilterOperator operator = convertFilterOperator(filter.getOpEnum());
            String value = valueToString(property.getValue());

            filterList.add(new QueryFilterDto(propertyName, operator, value));
        }

        final ArrayList<QueryOrderDto> orderList = new ArrayList<QueryOrderDto>();
        for (DatastorePb.Query.Order order : query.orders()) {
            QueryOrderDirection direction = convertOrderDirection(order.getDirectionEnum());
            String property = order.getProperty();

            orderList.add(new QueryOrderDto(direction, property));
        }

        Integer offset = query.hasOffset() ? query.getOffset() : null;
        Integer limit = query.hasLimit() ? query.getLimit() : null;

        return new QueryDto(kind, ancestor, filterList, orderList, offset, limit);
    }

    private static QueryFilterOperator convertFilterOperator(DatastorePb.Query.Filter.Operator operator) {
        switch (operator) {
            case EQUAL:
                return QueryFilterOperator.EQUAL;

            case GREATER_THAN:
                return QueryFilterOperator.GREATER_THAN;

            case GREATER_THAN_OR_EQUAL:
                return QueryFilterOperator.GREATER_THAN_OR_EQUAL;

            case LESS_THAN:
                return QueryFilterOperator.LESS_THAN;

            case LESS_THAN_OR_EQUAL:
                return QueryFilterOperator.LESS_THAN_OR_EQUAL;

            default:
                throw new IllegalArgumentException("Unknown query filter operator: " + operator);
        }
    }

    private static QueryOrderDirection convertOrderDirection(DatastorePb.Query.Order.Direction direction) {
        switch (direction) {
            case ASCENDING:
                return QueryOrderDirection.ASCENDING;

            case DESCENDING:
                return QueryOrderDirection.DESCENDING;

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
        } else if (value.hasReferenceValue()) {
            return value.getReferenceValue().toString();
        } else {
            return value.toString();
        }
    }
}
