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

import com.google.appengine.api.datastore.Query;
import com.google.common.collect.Iterables;

public class DevServerDatastoreCountProvider implements DatastoreCountProvider {
    private final DatastoreHelper datastoreHelper;

    @Inject
    DevServerDatastoreCountProvider(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public long get(String kind, String namespace) {
        Query query = new Query(kind).setKeysOnly();

        return Iterables.size(datastoreHelper.queryOnNamespace(namespace, query));
    }
}
