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

package com.arcbees.gaestudio.server.service.visualizer;

import java.util.Collection;

import com.arcbees.gaestudio.server.exception.InvalidGqlSyntaxException;
import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.server.util.GqlQuery;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.inject.Inject;

public class GqlServiceImpl implements GqlService {
    private final DatastoreHelper datastoreHelper;

    @Inject
    GqlServiceImpl(
            DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public Collection<Entity> executeGqlRequest(String gqlRequest, Integer offset, Integer limit) {
        AppEngineHelper.disableApiHooks();
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        GqlQuery gql;

        if (offset != null) {
            fetchOptions.offset(offset);
        }
        if (limit != null) {
            fetchOptions.limit(limit);
        }

        try {
            gql = new GqlQuery(gqlRequest);

            return datastoreHelper.queryOnAllNamespaces(gql.query(), fetchOptions);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidGqlSyntaxException();
        }
    }
}
