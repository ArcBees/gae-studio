/*
 * Copyright 2012 ArcBees Inc.
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

package com.arcbees.gae.querylogger.common.formatters;

import com.google.appengine.repackaged.com.google.io.protocol.ProtocolMessage;
import com.google.apphosting.api.DatastorePb.GetRequest;
import com.google.apphosting.api.DatastorePb.GetResponse;
import com.google.apphosting.api.DatastorePb.PutRequest;
import com.google.apphosting.api.DatastorePb.PutResponse;
import com.google.apphosting.api.DatastorePb.Query;
import com.google.apphosting.api.DatastorePb.QueryResult;

import com.arcbees.gae.querylogger.common.dto.GetRecord;
import com.arcbees.gae.querylogger.common.dto.PutRecord;
import com.arcbees.gae.querylogger.common.dto.QueryRecord;
import com.google.storage.onestore.v3.OnestoreEntity;

import java.util.HashMap;
import java.util.Map;

public class ObjectifyRecordFormatter extends AbstractRecordFormatter {
    
    private static final Map<Query.Filter.Operator, String> FILTER_OP_SYMBOLS =
            new HashMap<Query.Filter.Operator, String>();
    
    static {
        FILTER_OP_SYMBOLS.put(Query.Filter.Operator.EQUAL, "=");
        FILTER_OP_SYMBOLS.put(Query.Filter.Operator.GREATER_THAN, ">");
        FILTER_OP_SYMBOLS.put(Query.Filter.Operator.GREATER_THAN_OR_EQUAL, ">=");
        FILTER_OP_SYMBOLS.put(Query.Filter.Operator.LESS_THAN, "<");
        FILTER_OP_SYMBOLS.put(Query.Filter.Operator.LESS_THAN_OR_EQUAL, "<=");
    }

    @Override
    public String formatRecord(GetRecord record) {
        return "Get record formatting not implemented yet";
    }

    @Override
    public String formatRecord(PutRecord record) {
        return "Put record formatting not implemented yet";
    }

    @Override
    public String formatRecord(QueryRecord record) {
        final StringBuilder builder = new StringBuilder();
        final Query query = record.getQuery();

        if (query.hasKind()) {
            builder.append("query(");
            builder.append(query.getKind());
            builder.append(".class)");
        }

        if (query.hasAncestor()) {
            builder.append(".ancestor(");
            builder.append(query.getAncestor().toString());
            builder.append(")");
        }
        
        for (Query.Filter filter : query.filters()) {
            // TODO find out why this is an array
            OnestoreEntity.Property property = filter.getProperty(0);
            builder.append(".filter(\"");
            builder.append(property.getName()); 
            builder.append(" ");
            builder.append(operatorToString(filter.getOpEnum()));
            builder.append("\", ");
            builder.append(valueToString(property.getValue()));
            builder.append(")");
        }

        for (Query.Order order : query.orders()) {
            builder.append(".order(\"");
            if (order.getDirectionEnum() == Query.Order.Direction.DESCENDING) {
                builder.append("-");
            }
            builder.append(order.getProperty());
            builder.append("\")");
        }

        if (query.hasOffset()) {
            builder.append(".offset(");
            builder.append(query.getOffset());
            builder.append(")");
        }

        if (query.hasLimit()) {
            builder.append(".limit(");
            builder.append(query.getLimit());
            builder.append(")");
        }

        return builder.toString();
    }
    
    private String operatorToString(Query.Filter.Operator operator) {
        return FILTER_OP_SYMBOLS.containsKey(operator)
                ? FILTER_OP_SYMBOLS.get(operator)
                : operator.toString();
    }

    // TODO find out if there's a built-in way to do this
    private String valueToString(OnestoreEntity.PropertyValue value) {
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
