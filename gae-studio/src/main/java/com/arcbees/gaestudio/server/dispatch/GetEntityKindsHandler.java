/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import java.util.ArrayList;
import java.util.List;

public class GetEntityKindsHandler
        extends AbstractActionHandler<GetEntityKindsAction, GetEntityKindsResult> {

    public GetEntityKindsHandler() {
        super(GetEntityKindsAction.class);
    }

    @Override
    public GetEntityKindsResult execute(GetEntityKindsAction action, ExecutionContext context)
            throws ActionException {

        // TODO use the skip and take parameters for pagination

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Iterable<Entity> entityKinds = datastore.prepare(new Query("__Stat_Kind__")).asIterable();
        
        List<String> entityKindNames = new ArrayList<String>();
        
        for (Entity entity : entityKinds) {
            entityKindNames.add((String)entity.getProperty("kind_name"));
        }

        return new GetEntityKindsResult(entityKindNames);
    }

    @Override
    public void undo(GetEntityKindsAction action, GetEntityKindsResult result, ExecutionContext context)
            throws ActionException {
    }

}
