/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dto.mapper;

import com.arcbees.gaestudio.shared.dto.FilterDTO;
import com.arcbees.gaestudio.shared.dto.FilterOperator;
import com.arcbees.gaestudio.shared.dto.OrderDirection;
import com.arcbees.gaestudio.shared.dto.QueryDTO;
import com.arcbees.gaestudio.shared.dto.QueryOrderDTO;
import com.google.apphosting.api.DatastorePb;
import com.google.storage.onestore.v3.OnestoreEntity;

import java.util.ArrayList;

public class QueryMapper {
    
    @SuppressWarnings("unused")
    private QueryMapper() {
    }
    
    public static QueryDTO mapDTO(DatastorePb.Query query) {
        String kind = query.hasKind() ? query.getKind() : null;
        String ancestor = query.hasAncestor() ? query.getAncestor().toString() : null;
        
        final ArrayList<FilterDTO> filterList = new ArrayList<FilterDTO>();
        for (DatastorePb.Query.Filter filter : query.filters()) {
            // TODO find out why this is an array
            OnestoreEntity.Property property = filter.getProperty(0);
            String propertyName = property.getName();
            FilterOperator operator = convertFilterOperator(filter.getOpEnum());
            String value = valueToString(property.getValue());
            
            filterList.add(new FilterDTO(propertyName, operator, value));
        }
        
        final ArrayList<QueryOrderDTO> orderList = new ArrayList<QueryOrderDTO>();
        for (DatastorePb.Query.Order order : query.orders()) {
            OrderDirection direction = convertOrderDirection(order.getDirectionEnum());
            String property = order.getProperty();
            
            orderList.add(new QueryOrderDTO(direction, property));
        }
        
        Integer offset = query.hasOffset() ? query.getOffset() : null;
        Integer limit = query.hasLimit() ? query.getLimit() : null;

        return new QueryDTO(kind, ancestor, filterList, orderList, offset, limit);
    }

    private static FilterOperator convertFilterOperator(DatastorePb.Query.Filter.Operator operator) {
        switch (operator) {
            case EQUAL: return FilterOperator.EQUAL;
            case GREATER_THAN: return FilterOperator.GREATER_THAN;
            case GREATER_THAN_OR_EQUAL: return FilterOperator.GREATER_THAN_OR_EQUAL;
            case LESS_THAN: return FilterOperator.LESS_THAN;
            case LESS_THAN_OR_EQUAL: return FilterOperator.LESS_THAN_OR_EQUAL;
            // TODO add some kind of error handling here
            default: return null;
        }
    }
    
    private static OrderDirection convertOrderDirection(DatastorePb.Query.Order.Direction direction) {
        switch (direction) {
            case ASCENDING: return OrderDirection.ASCENDING;
            case DESCENDING: return OrderDirection.DESCENDING;
            // TODO add some kind of error handling here
            default: return null;
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
            throw new IllegalArgumentException("I don't know how to format " + value.toString());
        }
    }
    
}
