/**
 * Copyright 2015 ArcBees Inc.
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

package com.arcbees.gaestudio.server.service.profiler;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.recorder.HookRegistrar;
import com.arcbees.gaestudio.server.recorder.authentication.Listener;
import com.arcbees.gaestudio.server.recorder.authentication.ListenerProvider;

public class RecordServiceImpl implements RecordService {
    private final HookRegistrar hookRegistrar;
    private final ListenerProvider listenerProvider;

    @Inject
    RecordServiceImpl(
            HookRegistrar hookRegistrar,
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
