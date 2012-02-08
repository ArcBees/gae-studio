/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import java.util.logging.Logger;

public class GetEntitiesByKindHandler
        extends AbstractActionHandler<GetEntitiesByKindAction, GetEntitiesByKindResult> {
    
    private final Logger logger;
    
    @Inject
    public GetEntitiesByKindHandler(final Logger logger) {
        super(GetEntitiesByKindAction.class);
        this.logger = logger;
    }

    @Override
    public GetEntitiesByKindResult execute(GetEntitiesByKindAction action, ExecutionContext context)
            throws ActionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void undo(GetEntitiesByKindAction action, GetEntitiesByKindResult result, ExecutionContext context)
            throws ActionException {
        // Nothing to do here
    }

}
