/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GenerateSampleDataAction;
import com.arcbees.gaestudio.shared.dispatch.GenerateSampleDataResult;
import com.arcbees.gaestudio.shared.domain.Sprocket;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class GenerateSampleDataHandler
        extends AbstractActionHandler<GenerateSampleDataAction, GenerateSampleDataResult> {

    public GenerateSampleDataHandler() {
        super(GenerateSampleDataAction.class);
    }

    @Override
    public GenerateSampleDataResult execute(GenerateSampleDataAction action, ExecutionContext context)
            throws ActionException {

        Objectify objectify = ObjectifyService.begin();
        
        for (int i = 1; i <= 10; ++i) {
            Sprocket sprocket = new Sprocket("Sprocket #" + i);
            objectify.put(sprocket);
        }

        return new GenerateSampleDataResult();
    }

    @Override
    public void undo(GenerateSampleDataAction action, GenerateSampleDataResult result, ExecutionContext context)
            throws ActionException {
    }

}
