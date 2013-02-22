/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.server.recorder.HookRegistrar;
import com.arcbees.gaestudio.server.recorder.MemcacheKey;
import com.arcbees.gaestudio.server.recorder.authentication.Listener;
import com.arcbees.gaestudio.server.recorder.authentication.ListenerProvider;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingAction;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingResult;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class SetRecordingHandler extends AbstractActionHandler<SetRecordingAction, SetRecordingResult> {
    private final HookRegistrar hookRegistrar;
    private final ListenerProvider listenerProvider;
    private final MemcacheService memcacheService;

    @Inject
    public SetRecordingHandler(final HookRegistrar hookRegistrar, final ListenerProvider listenerProvider,
            final MemcacheService memcacheService) {
        super(SetRecordingAction.class);

        this.hookRegistrar = hookRegistrar;
        this.listenerProvider = listenerProvider;
        this.memcacheService = memcacheService;
    }

    @Override
    public SetRecordingResult execute(SetRecordingAction action, ExecutionContext context) throws ActionException {
        Listener listener = listenerProvider.get();

        if (action.isStarting()) {
            hookRegistrar.putListener(listener);
        } else {
            hookRegistrar.removeListener(listener);
        }

        return new SetRecordingResult(getMostRecentId());
    }

    private Long getMostRecentId() {
        Long mostRecentId = (Long) memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName());
        if (mostRecentId == null) {
            mostRecentId = 0L;
        }
        return mostRecentId;
    }
}
