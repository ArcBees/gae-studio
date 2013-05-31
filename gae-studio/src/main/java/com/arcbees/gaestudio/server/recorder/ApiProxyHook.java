/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.recorder;

import com.google.apphosting.api.ApiProxy.ApiConfig;
import com.google.apphosting.api.ApiProxy.ApiProxyException;
import com.google.apphosting.api.ApiProxy.Delegate;
import com.google.apphosting.api.ApiProxy.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// TODO externalize magic strings
// TODO cleanup
public class ApiProxyHook extends BaseHook {
    private Map<String, Delegate<Environment>> hooks;
    boolean isRecording = false;

    public ApiProxyHook(Delegate<Environment> baseDelegate) {
        super(baseDelegate);
        hooks = new HashMap<String, Delegate<Environment>>();
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

        // TODO flesh this out and make sure it works in all scenarios

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
        return (isDisabledForRequest(environment) || !isRecording);
    }

    public void setRecording(boolean recording) {
        isRecording = recording;
    }

    private Boolean isDisabledForRequest(Environment environment) {
        Boolean setting = (Boolean) environment.getAttributes().get("GaeStudio.disableApiHooks");
        if (setting != null && setting) {
            return true;
        }
        return false;
    }
}
