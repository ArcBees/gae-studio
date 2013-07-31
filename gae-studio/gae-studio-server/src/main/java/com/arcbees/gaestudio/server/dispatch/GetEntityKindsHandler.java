/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dispatch;

import java.util.ArrayList;
import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.DatastoreHelper;
import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetEntityKindsHandler extends AbstractActionHandler<GetEntityKindsAction, GetEntityKindsResult> {
    private static final String GET_ENTITY_KINDS = "Get Entity Kinds";

    private final GoogleAnalytic googleAnalytic;
    private final DatastoreHelper datastoreHelper;

    @Inject
    GetEntityKindsHandler(GoogleAnalytic googleAnalytic,
                          DatastoreHelper datastoreHelper) {
        super(GetEntityKindsAction.class);

        this.googleAnalytic = googleAnalytic;
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public GetEntityKindsResult execute(GetEntityKindsAction action,
                                        ExecutionContext context) throws ActionException {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_ENTITY_KINDS);

        DispatchHelper.disableApiHooks();

        Query query = new Query(Entities.KIND_METADATA_KIND);
        Iterable<Entity> entityIterable = datastoreHelper.queryOnAllNamespaces(query);

        ArrayList<String> kinds = getKinds(entityIterable);

        return new GetEntityKindsResult(kinds);
    }

    private ArrayList<String> getKinds(Iterable<Entity> entityIterable) {
        Set<String> kinds = FluentIterable.from(entityIterable).transform(new Function<Entity, String>() {
            @Override
            public String apply(Entity entity) {
                String kindName;

                if (entity != null) {
                    kindName = entity.getKey().getName();
                } else {
                    kindName = "";
                }

                return kindName;
            }
        }).toSet();

        return Lists.newArrayList(kinds);
    }
}
