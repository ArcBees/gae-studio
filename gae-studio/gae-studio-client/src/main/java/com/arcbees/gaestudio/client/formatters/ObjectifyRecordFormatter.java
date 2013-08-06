/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.formatters;

import java.util.HashMap;

import com.arcbees.gaestudio.client.dto.DeleteRecordDto;
import com.arcbees.gaestudio.client.dto.GetRecordDto;
import com.arcbees.gaestudio.client.dto.PutRecordDto;
import com.arcbees.gaestudio.client.dto.query.QueryDto;
import com.arcbees.gaestudio.client.dto.query.QueryFilterDto;
import com.arcbees.gaestudio.shared.QueryFilterOperator;
import com.arcbees.gaestudio.shared.QueryOrderDirection;
import com.arcbees.gaestudio.client.dto.query.QueryOrderDto;
import com.arcbees.gaestudio.client.dto.query.QueryRecordDto;

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
            builder.append(filter.getValue());
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

