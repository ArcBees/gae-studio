/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dto.mapper;

import com.arcbees.gaestudio.shared.dto.QueryFilter;
import com.arcbees.gaestudio.shared.dto.QueryFilterOperator;
import com.arcbees.gaestudio.shared.dto.QueryOrderDirection;
import com.arcbees.gaestudio.shared.dto.Query;
import com.arcbees.gaestudio.shared.dto.QueryOrder;
import com.google.apphosting.api.DatastorePb;
import com.google.storage.onestore.v3.OnestoreEntity;

import java.util.ArrayList;

public class QueryMapper {
    
    @SuppressWarnings("unused")
    private QueryMapper() {
    }
    
    public static Query mapDTO(DatastorePb.Query query) {
        String kind = query.hasKind() ? query.getKind() : null;
        String ancestor = query.hasAncestor() ? query.getAncestor().toString() : null;
        
        final ArrayList<QueryFilter> filterList = new ArrayList<QueryFilter>();
        for (DatastorePb.Query.Filter filter : query.filters()) {
            // TODO find out why this is an array
            OnestoreEntity.Property property = filter.getProperty(0);
            String propertyName = property.getName();
            QueryFilterOperator operator = convertFilterOperator(filter.getOpEnum());
            String value = valueToString(property.getValue());
            
            filterList.add(new QueryFilter(propertyName, operator, value));
        }
        
        final ArrayList<QueryOrder> orderList = new ArrayList<QueryOrder>();
        for (DatastorePb.Query.Order order : query.orders()) {
            QueryOrderDirection direction = convertOrderDirection(order.getDirectionEnum());
            String property = order.getProperty();
            
            orderList.add(new QueryOrder(direction, property));
        }
        
        Integer offset = query.hasOffset() ? query.getOffset() : null;
        Integer limit = query.hasLimit() ? query.getLimit() : null;

        return new Query(kind, ancestor, filterList, orderList, offset, limit);
    }

    private static QueryFilterOperator convertFilterOperator(DatastorePb.Query.Filter.Operator operator) {
        switch (operator) {
            case EQUAL: return QueryFilterOperator.EQUAL;
            case GREATER_THAN: return QueryFilterOperator.GREATER_THAN;
            case GREATER_THAN_OR_EQUAL: return QueryFilterOperator.GREATER_THAN_OR_EQUAL;
            case LESS_THAN: return QueryFilterOperator.LESS_THAN;
            case LESS_THAN_OR_EQUAL: return QueryFilterOperator.LESS_THAN_OR_EQUAL;
            // TODO add some kind of error handling here
            default: return null;
        }
    }
    
    private static QueryOrderDirection convertOrderDirection(DatastorePb.Query.Order.Direction direction) {
        switch (direction) {
            case ASCENDING: return QueryOrderDirection.ASCENDING;
            case DESCENDING: return QueryOrderDirection.DESCENDING;
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
