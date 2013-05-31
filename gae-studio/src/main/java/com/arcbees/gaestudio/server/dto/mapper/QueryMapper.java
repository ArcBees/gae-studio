/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dto.mapper;

import com.arcbees.gaestudio.shared.dto.query.*;
import com.google.apphosting.api.DatastorePb;
import com.google.storage.onestore.v3.OnestoreEntity;

import java.util.ArrayList;

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
