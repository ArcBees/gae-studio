/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.server.recorder.HookDriver;
import com.arcbees.gaestudio.server.recorder.authentication.Listener;
import com.arcbees.gaestudio.server.recorder.authentication.ListenerProvider;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingAction;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import java.util.logging.Logger;

// TODO externalize magic strings
// TODO add logging
public class SetRecordingHandler
        extends AbstractActionHandler<SetRecordingAction, SetRecordingResult> {

    private final Logger logger;
    private final HookDriver hookDriver;
    private final ListenerProvider listenerProvider;

    @Inject
    public SetRecordingHandler(final Logger logger, final HookDriver hookDriver,
                               final ListenerProvider listenerProvider) {
        super(SetRecordingAction.class);

        this.logger = logger;
        this.hookDriver = hookDriver;
        this.listenerProvider = listenerProvider;
    }

    @Override
    public SetRecordingResult execute(SetRecordingAction action, ExecutionContext context)
            throws ActionException {
        Listener listener = listenerProvider.get();

        if (action.isStart()) {
            hookDriver.putListener(listener);
        } else {
            hookDriver.removeListener(listener);
        }

        return new SetRecordingResult();
    }

    @Override
    public void undo(SetRecordingAction setRecordingAction, SetRecordingResult setRecordingResult, ExecutionContext
            executionContext) throws ActionException {
        // Nothing to do here
    }

}
