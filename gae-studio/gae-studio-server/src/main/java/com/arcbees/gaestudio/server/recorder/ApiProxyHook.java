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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.google.apphosting.api.ApiProxy.ApiConfig;
import com.google.apphosting.api.ApiProxy.ApiProxyException;
import com.google.apphosting.api.ApiProxy.Delegate;
import com.google.apphosting.api.ApiProxy.Environment;

public class ApiProxyHook extends BaseHook {
    private Map<String, Delegate<Environment>> hooks;
    private boolean isRecording;

    public ApiProxyHook(Delegate<Environment> baseDelegate) {
        super(baseDelegate);

        hooks = new HashMap<>();
    }

    @Override
    public byte[] makeSyncCall(Environment environment, String packageName, String methodName, byte[] request)
            throws ApiProxyException {
        Delegate<Environment> hook = hooks.get(packageName);
        if (hook != null && !areApiHooksDisabled(environment)) {
            return hook.makeSyncCall(environment, packageName, methodName, request);
        } else {
            return getBaseDelegate().makeSyncCall(environment, packageName, methodName, request);
        }
    }

    @Override
    public Future<byte[]> makeAsyncCall(final Environment environment,
            final String packageName,
            final String methodName,
            final byte[] request,
            ApiConfig apiConfig) {

        if (areApiHooksDisabled(environment)) {
            return getBaseDelegate().makeAsyncCall(environment, packageName, methodName, request, apiConfig);
        }

        return new Future<byte[]>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public byte[] get() throws InterruptedException, ExecutionException {
                return makeSyncCall(environment, packageName, methodName, request);
            }

            @Override
            public byte[] get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException,
                    TimeoutException {
                return new byte[0];
            }
        };
    }

    public Map<String, Delegate<Environment>> getHooks() {
        return hooks;
    }

    public boolean areApiHooksDisabled(Environment environment) {
        return isDisabledForRequest(environment) || !isRecording;
    }

    public void setRecording(boolean recording) {
        isRecording = recording;
    }

    private Boolean isDisabledForRequest(Environment environment) {
        Boolean setting = (Boolean) environment.getAttributes().get(GaeStudioConstants.DISABLE_API_HOOKS);

        return setting != null && setting;
    }
}
