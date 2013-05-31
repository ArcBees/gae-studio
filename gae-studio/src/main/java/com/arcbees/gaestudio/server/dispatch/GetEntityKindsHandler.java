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

import javax.annotation.Nullable;

import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.google.appengine.api.datastore.*;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetEntityKindsHandler extends AbstractActionHandler<GetEntityKindsAction, GetEntityKindsResult> {
    @Inject
    GetEntityKindsHandler() {
        super(GetEntityKindsAction.class);
    }

    @Override
    public GetEntityKindsResult execute(GetEntityKindsAction action, ExecutionContext context)
            throws ActionException {
        DispatchHelper.disableApiHooks();

        Query query = new Query(Entities.KIND_METADATA_KIND);
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

        Iterable<Entity> entityIterable = datastoreService.prepare(query).asIterable();

        Iterable<String> iterator = Iterables.transform(entityIterable, new Function<Entity, String>() {
            @Override
            public String apply(@Nullable Entity entity) {
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
