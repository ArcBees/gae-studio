/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dto.mapper;

import com.arcbees.gaestudio.shared.dto.query.QueryDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryFilterDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryFilterOperatorDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryOrderDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryOrderDirectionDTO;
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
        
        final ArrayList<QueryFilterDTO> filterList = new ArrayList<QueryFilterDTO>();
        for (DatastorePb.Query.Filter filter : query.filters()) {
            // TODO find out why this is an array
            OnestoreEntity.Property property = filter.getProperty(0);
            String propertyName = property.getName();
            QueryFilterOperatorDTO operator = convertFilterOperator(filter.getOpEnum());
            String value = valueToString(property.getValue());
            
            filterList.add(new QueryFilterDTO(propertyName, operator, value));
        }
        
        final ArrayList<QueryOrderDTO> orderList = new ArrayList<QueryOrderDTO>();
        for (DatastorePb.Query.Order order : query.orders()) {
            QueryOrderDirectionDTO direction = convertOrderDirection(order.getDirectionEnum());
            String property = order.getProperty();
            
            orderList.add(new QueryOrderDTO(direction, property));
        }
        
        Integer offset = query.hasOffset() ? query.getOffset() : null;
        Integer limit = query.hasLimit() ? query.getLimit() : null;

        return new QueryDTO(kind, ancestor, filterList, orderList, offset, limit);
    }

    private static QueryFilterOperatorDTO convertFilterOperator(DatastorePb.Query.Filter.Operator operator) {
        switch (operator) {
            case EQUAL:
                return QueryFilterOperatorDTO.EQUAL;

            case GREATER_THAN:
                return QueryFilterOperatorDTO.GREATER_THAN;

            case GREATER_THAN_OR_EQUAL:
                return QueryFilterOperatorDTO.GREATER_THAN_OR_EQUAL;

            case LESS_THAN:
                return QueryFilterOperatorDTO.LESS_THAN;

            case LESS_THAN_OR_EQUAL:
                return QueryFilterOperatorDTO.LESS_THAN_OR_EQUAL;

            default:
                throw new IllegalArgumentException("Unknown query filter operator: " + operator);
        }
    }
    
    private static QueryOrderDirectionDTO convertOrderDirection(DatastorePb.Query.Order.Direction direction) {
        switch (direction) {
            case ASCENDING:
                return QueryOrderDirectionDTO.ASCENDING;

            case DESCENDING:
                return QueryOrderDirectionDTO.DESCENDING;

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
