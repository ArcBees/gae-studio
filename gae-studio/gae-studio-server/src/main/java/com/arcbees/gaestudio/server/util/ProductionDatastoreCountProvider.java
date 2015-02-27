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

package com.arcbees.gaestudio.server.util;

import javax.inject.Inject;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;

import static com.google.appengine.api.datastore.Query.FilterOperator.EQUAL;

public class ProductionDatastoreCountProvider implements DatastoreCountProvider {
    private final DatastoreHelper datastoreHelper;

    @Inject
    ProductionDatastoreCountProvider(
            DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public long get(String kind, String namespace) {
        String statsKind;
        if (namespace == null) {
            statsKind = "__Stat_Kind__";
        } else {
            statsKind = "__Stat_Ns_Kind__";
        }

        Query query = new Query(statsKind);
        query.setFilter(new Query.FilterPredicate("kind_name", EQUAL, kind));

        Entity stats = datastoreHelper.querySingleEntity(namespace, query);

        if (stats == null) {
            return 0;
        }

        return (long) stats.getProperty("count");
    }
}
