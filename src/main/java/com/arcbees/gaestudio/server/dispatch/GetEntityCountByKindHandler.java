/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindResult;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetEntityCountByKindHandler
        extends AbstractActionHandler<GetEntityCountByKindAction, GetEntityCountByKindResult> {

    @Inject
    public GetEntityCountByKindHandler() {
        super(GetEntityCountByKindAction.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public GetEntityCountByKindResult execute(GetEntityCountByKindAction action, ExecutionContext context)
            throws ActionException {
        DispatchHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query(action.getKind());
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        Integer count = datastore.prepare(query).countEntities(fetchOptions);

        return new GetEntityCountByKindResult(count);
    }

}
