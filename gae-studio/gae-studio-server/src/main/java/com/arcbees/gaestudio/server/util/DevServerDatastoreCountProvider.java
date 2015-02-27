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

import com.google.appengine.api.datastore.Query;
import com.google.common.collect.Iterables;

public class DevServerDatastoreCountProvider implements DatastoreCountProvider {
    private final DatastoreHelper datastoreHelper;

    @Inject
    DevServerDatastoreCountProvider(
            DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public long get(String kind, String namespace) {
        Query query = new Query(kind).setKeysOnly();

        return Iterables.size(datastoreHelper.queryOnNamespace(namespace, query));
    }
}
