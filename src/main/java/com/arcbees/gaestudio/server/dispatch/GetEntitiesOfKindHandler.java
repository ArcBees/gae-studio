/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetEntitiesOfKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesOfKindResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import java.util.logging.Logger;

public class GetEntitiesOfKindHandler
        extends AbstractActionHandler<GetEntitiesOfKindAction, GetEntitiesOfKindResult> {
    
    private final Logger logger;
    
    @Inject
    public GetEntitiesOfKindHandler(final Logger logger) {
        super(GetEntitiesOfKindAction.class);
        this.logger = logger;
    }

    @Override
    public GetEntitiesOfKindResult execute(GetEntitiesOfKindAction action, ExecutionContext context)
            throws ActionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void undo(GetEntitiesOfKindAction action, GetEntitiesOfKindResult result, ExecutionContext context)
            throws ActionException {
        // Nothing to do here
    }

}
