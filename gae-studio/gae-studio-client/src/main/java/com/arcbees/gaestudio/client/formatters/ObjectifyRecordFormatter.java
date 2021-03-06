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

package com.arcbees.gaestudio.client.formatters;

import java.util.HashMap;

import com.arcbees.gaestudio.shared.QueryFilterOperator;
import com.arcbees.gaestudio.shared.QueryOrderDirection;
import com.arcbees.gaestudio.shared.dto.DeleteRecordDto;
import com.arcbees.gaestudio.shared.dto.GetRecordDto;
import com.arcbees.gaestudio.shared.dto.PutRecordDto;
import com.arcbees.gaestudio.shared.dto.query.QueryDto;
import com.arcbees.gaestudio.shared.dto.query.QueryFilterDto;
import com.arcbees.gaestudio.shared.dto.query.QueryOrderDto;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDto;

public class ObjectifyRecordFormatter extends AbstractRecordFormatter {
    private static final HashMap<QueryFilterOperator, String> FILTER_OP_SYMBOLS =
            new HashMap<QueryFilterOperator, String>();

    static {
        FILTER_OP_SYMBOLS.put(QueryFilterOperator.EQUAL, "=");
        FILTER_OP_SYMBOLS.put(QueryFilterOperator.GREATER_THAN, ">");
        FILTER_OP_SYMBOLS.put(QueryFilterOperator.GREATER_THAN_OR_EQUAL, ">=");
        FILTER_OP_SYMBOLS.put(QueryFilterOperator.LESS_THAN, "<");
        FILTER_OP_SYMBOLS.put(QueryFilterOperator.LESS_THAN_OR_EQUAL, "<=");
    }

    @Override
    public String formatRecord(DeleteRecordDto record) {
        return "Delete record formatting not implemented yet";
    }

    @Override
    public String formatRecord(GetRecordDto record) {
        return "Get record formatting not implemented yet";
    }

    @Override
    public String formatRecord(PutRecordDto record) {
        return "Put record formatting not implemented yet";
    }

    @Override
    public String formatRecord(QueryRecordDto record) {
        final StringBuilder builder = new StringBuilder();
        final QueryDto query = record.getQuery();

        builder.append("query(");
        if (query.getKind() != null) {
            builder.append(query.getKind());
            builder.append(".class");
        }
        builder.append(")");

        if (query.getAncestor() != null) {
            builder.append(".ancestor(");
            builder.append(query.getAncestor());
            builder.append(")");
        }

        for (QueryFilterDto filter : query.getFilters()) {
            builder.append(".filter(\"");
            builder.append(filter.getProperty());
            builder.append(" ");
            builder.append(operatorToString(filter.getOperator()));
            builder.append("\", ");
            String value = filter.getValue();
            builder.append(value.replace('\ufffd' + "", "\\ufffd"));
            builder.append(")");
        }

        for (QueryOrderDto order : query.getOrders()) {
            builder.append(".order(\"");
            if (order.getDirection() == QueryOrderDirection.DESCENDING) {
                builder.append("-");
            }
            builder.append(order.getProperty());
            builder.append("\")");
        }

        if (query.getOffset() != null) {
            builder.append(".offset(");
            builder.append(query.getOffset());
            builder.append(")");
        }

        if (query.getOffset() != null) {
            builder.append(".limit(");
            builder.append(query.getLimit());
            builder.append(")");
        }

        return builder.toString();
    }

    private String operatorToString(QueryFilterOperator operator) {
        return FILTER_OP_SYMBOLS.containsKey(operator)
                ? FILTER_OP_SYMBOLS.get(operator)
                : operator.toString();
    }
}

