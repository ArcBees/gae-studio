/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
    GqlServiceImpl(DatastoreHelper datastoreHelper) {
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
