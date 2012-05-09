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

package com.arcbees.gaestudio.shared.formatters;

import com.arcbees.gaestudio.shared.dto.DeleteRecordDTO;
import com.arcbees.gaestudio.shared.dto.GetRecordDTO;
import com.arcbees.gaestudio.shared.dto.PutRecordDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryFilterDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryFilterOperatorDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryOrderDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryOrderDirectionDTO;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDTO;

import java.util.HashMap;

public class ObjectifyRecordFormatter extends AbstractRecordFormatter {
    
    private static final HashMap<QueryFilterOperatorDTO, String> FILTER_OP_SYMBOLS =
            new HashMap<QueryFilterOperatorDTO, String>();

    static {
        FILTER_OP_SYMBOLS.put(QueryFilterOperatorDTO.EQUAL, "=");
        FILTER_OP_SYMBOLS.put(QueryFilterOperatorDTO.GREATER_THAN, ">");
        FILTER_OP_SYMBOLS.put(QueryFilterOperatorDTO.GREATER_THAN_OR_EQUAL, ">=");
        FILTER_OP_SYMBOLS.put(QueryFilterOperatorDTO.LESS_THAN, "<");
        FILTER_OP_SYMBOLS.put(QueryFilterOperatorDTO.LESS_THAN_OR_EQUAL, "<=");
    }
    
    @Override
    public String formatRecord(DeleteRecordDTO record) {
        return "Delete record formatting not implemented yet";
    }

    @Override
    public String formatRecord(GetRecordDTO record) {
        return "Get record formatting not implemented yet";
    }

    @Override
    public String formatRecord(PutRecordDTO record) {
        return "Put record formatting not implemented yet";
    }

    @Override
    public String formatRecord(QueryRecordDTO record) {
        final StringBuilder builder = new StringBuilder();
        final QueryDTO query = record.getQuery();

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
        
        for (QueryFilterDTO filter : query.getFilters()) {
            builder.append(".filter(\"");
            builder.append(filter.getProperty());
            builder.append(" ");
            builder.append(operatorToString(filter.getOperator()));
            builder.append("\", ");
            builder.append(filter.getValue());
            builder.append(")");
        }

        for (QueryOrderDTO order : query.getOrders()) {
            builder.append(".order(\"");
            if (order.getDirection() == QueryOrderDirectionDTO.DESCENDING) {
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
    
    private String operatorToString(QueryFilterOperatorDTO operator) {
        return FILTER_OP_SYMBOLS.containsKey(operator)
                ? FILTER_OP_SYMBOLS.get(operator)
                : operator.toString();
    }

}

