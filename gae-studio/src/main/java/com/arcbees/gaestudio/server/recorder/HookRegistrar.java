/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.recorder;

import com.arcbees.gaestudio.server.recorder.authentication.Listener;
import com.google.apphosting.api.ApiProxy;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class HookRegistrar {
    private final Logger logger;
    private final ApiProxyHook hook;
    private final Set<Listener> listeners = new HashSet<Listener>();

    @Inject
    @SuppressWarnings("unchecked")
    HookRegistrar(DbOperationRecorderHookFactory dbOperationRecorderHookFactory,
                  Logger logger) {
        this.logger = logger;
        hook = new ApiProxyHook(ApiProxy.getDelegate());
        hook.getHooks().put("datastore_v3", dbOperationRecorderHookFactory.create(hook.getBaseDelegate()));
        ApiProxy.setDelegate(hook);
    }

    synchronized public void putListener(Listener listener) {
        listeners.add(listener);
        setRecording();
    }

    synchronized public void removeListener(Listener listener) {
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
