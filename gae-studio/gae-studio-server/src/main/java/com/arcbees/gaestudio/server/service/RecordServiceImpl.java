/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.recorder.HookRegistrar;
import com.arcbees.gaestudio.server.recorder.authentication.Listener;
import com.arcbees.gaestudio.server.recorder.authentication.ListenerProvider;

public class RecordServiceImpl implements RecordService {
    private final HookRegistrar hookRegistrar;
    private final ListenerProvider listenerProvider;

    @Inject
    RecordServiceImpl(HookRegistrar hookRegistrar,
            ListenerProvider listenerProvider) {
        this.hookRegistrar = hookRegistrar;
        this.listenerProvider = listenerProvider;
    }

    @Override
    public void startRecording() {
        Listener listener = listenerProvider.get();
        hookRegistrar.putListener(listener);
    }

    @Override
    public void stopRecording() {
        Listener listener = listenerProvider.get();
        hookRegistrar.removeListener(listener);
    }
}
