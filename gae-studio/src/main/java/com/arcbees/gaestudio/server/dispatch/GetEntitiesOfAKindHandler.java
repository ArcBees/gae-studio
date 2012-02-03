/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetEntitiesOfAKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesOfAKindResult;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetEntitiesOfAKindHandler
        extends AbstractActionHandler<GetEntitiesOfAKindAction, GetEntitiesOfAKindResult> {

    public GetEntitiesOfAKindHandler() {
        super(GetEntitiesOfAKindAction.class);
    }

    @Override
    public GetEntitiesOfAKindResult execute(GetEntitiesOfAKindAction action, ExecutionContext context)
            throws ActionException {

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void undo(GetEntitiesOfAKindAction action, GetEntitiesOfAKindResult result, ExecutionContext context)
            throws ActionException {

        //To change body of implemented methods use File | Settings | File Templates.
    }
}
