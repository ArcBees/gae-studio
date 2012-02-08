/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.utils.SystemProperty;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// TODO add logging
public class GetEntityKindsHandler
        extends AbstractActionHandler<GetEntityKindsAction, GetEntityKindsResult> {
    
    private final Logger logger;

    @Inject
    public GetEntityKindsHandler(final Logger logger) {
        super(GetEntityKindsAction.class);
        this.logger = logger;
    }

    @Override
    public GetEntityKindsResult execute(GetEntityKindsAction action, ExecutionContext context)
            throws ActionException {

        DispatchHelper.disableApiHooks();

        ArrayList<String> kinds;
        if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            List<Entity> results =
                    datastore.prepare(new Query("__Stat_Kind__")).asList(FetchOptions.Builder.withDefaults());
            kinds = new ArrayList<String>(results.size());
            for (Entity statKind : results) {
                kinds.add((String)statKind.getProperty("kind_name"));
            }
        } else {
            // Querying by Stat_Kind does not currently work in the development environment
            kinds = new ArrayList<String>();
            // TODO externalize this
            kinds.add("Sprocket");
        }

        return new GetEntityKindsResult(kinds);
    }

    @Override
    public void undo(GetEntityKindsAction action, GetEntityKindsResult result, ExecutionContext context)
            throws ActionException {
        // Nothing to do here
    }

}
