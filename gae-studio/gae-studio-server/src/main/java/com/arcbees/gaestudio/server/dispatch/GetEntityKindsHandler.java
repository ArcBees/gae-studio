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

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetEntityKindsHandler extends AbstractActionHandler<GetEntityKindsAction, GetEntityKindsResult> {
    private final GoogleAnalytic googleAnalytic;

    @Inject
    GetEntityKindsHandler(GoogleAnalytic googleAnalytic) {
        super(GetEntityKindsAction.class);

        this.googleAnalytic = googleAnalytic;
    }

    @Override
    public GetEntityKindsResult execute(GetEntityKindsAction action,
                                        ExecutionContext context) throws ActionException {
        googleAnalytic.trackEvent("Server Call", "Get Entity Kinds");

        DispatchHelper.disableApiHooks();

        Query query = new Query(Entities.KIND_METADATA_KIND);
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

        Iterable<Entity> entityIterable = datastoreService.prepare(query).asIterable();

        Iterable<String> iterator = Iterables.transform(entityIterable, new Function<Entity, String>() {
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
        });

        ArrayList<String> kinds = Lists.newArrayList(iterator);

        return new GetEntityKindsResult(kinds);
    }
}
