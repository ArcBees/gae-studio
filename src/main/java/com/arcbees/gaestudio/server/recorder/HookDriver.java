/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.server.recorder;

import com.arcbees.gaestudio.server.recorder.authentication.Listener;
import com.google.apphosting.api.ApiProxy;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class HookDriver {

    private final Logger logger;
    final ApiProxyHook hook;
    final Set<Listener> listeners = new HashSet<Listener>();

    @Inject
    public HookDriver(final DbOperationRecorderHookFactory dbOperationRecorderHookFactory, final Logger logger) {
        this.logger = logger;
        hook = new ApiProxyHook(ApiProxy.getDelegate());
        hook.getHooks().put("datastore_v3", dbOperationRecorderHookFactory.create(hook.getBaseDelegate()));
        ApiProxy.setDelegate(hook);
    }

    public void putListener(Listener listener) {
        listeners.add(listener);
        setRecording();
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
        setRecording();
    }

    private void setRecording() {
        if (listeners.isEmpty()) {
            hook.setRecording(false);
            logger.info("Stop recording");
        } else {
            hook.setRecording(true);
            logger.info("Recording, " + listeners.size() + " listeners left");
        }
    }

}
