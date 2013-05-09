/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
    SetRecordingHandler(HookRegistrar hookRegistrar,
                        ListenerProvider listenerProvider,
                        MemcacheService memcacheService) {
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
