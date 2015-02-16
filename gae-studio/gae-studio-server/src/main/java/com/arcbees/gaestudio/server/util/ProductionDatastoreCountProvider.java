/**
 * Copyright (c) 2015 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import javax.inject.Inject;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;

import static com.google.appengine.api.datastore.Query.FilterOperator.EQUAL;

public class ProductionDatastoreCountProvider implements DatastoreCountProvider {
    private final DatastoreHelper datastoreHelper;

    @Inject
    ProductionDatastoreCountProvider(DatastoreHelper datastoreHelper) {
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
