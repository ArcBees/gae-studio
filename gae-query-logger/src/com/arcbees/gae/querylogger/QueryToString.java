/*
 * Copyright 2011 ArcBees Inc.
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

package com.arcbees.gae.querylogger;

import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

import java.util.List;

class QueryToString {

    static String queryToString(Query query, FetchOptions fetchOptions) {
        final StringBuilder builder = new StringBuilder();

        final String kind = query.getKind();
        builder.append("query(");
        builder.append(kind);
        builder.append(".class)");

        final Key ancestor = query.getAncestor();
        if (ancestor != null) {
            builder.append(".ancestor(");
            builder.append(ancestor.toString());
            builder.append(")");
        }

        final List<Query.FilterPredicate> filterPredicateList = query.getFilterPredicates();
        for (Query.FilterPredicate filterPredicate : filterPredicateList) {
            builder.append(".filter(\"");
            builder.append(filterPredicate.getPropertyName());
            builder.append(" ");
            builder.append(filterPredicate.getOperator().toString());
            builder.append("\", \"");
            builder.append(filterPredicate.getValue());
            builder.append("\")");
        }

        final List<Query.SortPredicate> sortPredicateList = query.getSortPredicates();
        for (Query.SortPredicate sortPredicate : sortPredicateList) {
            builder.append(".order(\"");
            if (sortPredicate.getDirection() == Query.SortDirection.DESCENDING) {
                builder.append("-");
            }
            builder.append(sortPredicate.getPropertyName());
            builder.append("\")");
        }

        Integer offset = fetchOptions.getOffset();
        if (offset != null) {
            builder.append(".offset(");
            builder.append(offset);
            builder.append(")");
        }
        
        Integer limit = fetchOptions.getLimit();
        if (limit != null) {
            builder.append(".limit(");
            builder.append(limit);
            builder.append(")");
        }

        return builder.toString();
    }

}
