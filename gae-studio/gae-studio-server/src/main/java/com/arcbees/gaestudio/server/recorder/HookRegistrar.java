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

package com.arcbees.gaestudio.server.recorder;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.recorder.authentication.Listener;
import com.google.apphosting.api.ApiProxy;

public class HookRegistrar {
    private final ApiProxyHook hook;
    private final Set<Listener> listeners = new HashSet<Listener>();

    @Inject
    @SuppressWarnings("unchecked")
    HookRegistrar(
            DbOperationRecorderHookFactory dbOperationRecorderHookFactory) {
        hook = new ApiProxyHook(ApiProxy.getDelegate());
        hook.getHooks().put("datastore_v3", dbOperationRecorderHookFactory.create(hook.getBaseDelegate()));
        ApiProxy.setDelegate(hook);
    }

    public synchronized void putListener(Listener listener) {
        listeners.add(listener);
        setRecording();
    }

    public synchronized void removeListener(Listener listener) {
        listeners.remove(listener);
        setRecording();
    }

    private void setRecording() {
        if (listeners.isEmpty()) {
            hook.setRecording(false);
        } else {
            hook.setRecording(true);
        }
    }
}
