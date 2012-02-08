/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import java.util.logging.Logger;

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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void undo(GetEntityKindsAction action, GetEntityKindsResult result, ExecutionContext context)
            throws ActionException {
        // Nothing to do here
    }

}
