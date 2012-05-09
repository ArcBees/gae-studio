/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// TODO add logging
public class GetEntitiesByKindHandler
        extends AbstractActionHandler<GetEntitiesByKindAction, GetEntitiesByKindResult> {
    
    private final Logger logger;
    
    @Inject
    public GetEntitiesByKindHandler(final Logger logger) {
        super(GetEntitiesByKindAction.class);
        this.logger = logger;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GetEntitiesByKindResult execute(GetEntitiesByKindAction action, ExecutionContext context)
            throws ActionException {

        DispatchHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        if (action.getOffset() != null) {
            fetchOptions.offset(action.getOffset());
        }
        if (action.getLimit() != null) {
            fetchOptions.limit(action.getLimit());
        }

        Query query = new Query(action.getKind());
        List<com.google.appengine.api.datastore.Entity> results = datastore.prepare(query).asList(fetchOptions);
        
        ArrayList<EntityDTO> entities = new ArrayList<EntityDTO>(results.size());
        for (com.google.appengine.api.datastore.Entity dbEntity : results) {
            entities.add(EntityMapper.mapDTO(dbEntity));
        }
        
        return new GetEntitiesByKindResult(entities);
    }

    @Override
    public void undo(GetEntitiesByKindAction action, GetEntitiesByKindResult result, ExecutionContext context)
            throws ActionException {
        // Nothing to do here
    }

}
