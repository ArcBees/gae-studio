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

package com.arcbees.gaestudio.server.recorder;

import java.util.List;
import java.util.concurrent.Future;

import com.google.apphosting.api.ApiProxy.ApiConfig;
import com.google.apphosting.api.ApiProxy.ApiProxyException;
import com.google.apphosting.api.ApiProxy.Delegate;
import com.google.apphosting.api.ApiProxy.Environment;
import com.google.apphosting.api.ApiProxy.LogRecord;

public class BaseHook implements Delegate<Environment> {
    private final Delegate<Environment> baseDelegate;

    public BaseHook(Delegate<Environment> baseDelegate) {
        this.baseDelegate = baseDelegate;
    }

    @Override
    public byte[] makeSyncCall(Environment environment, String packageName, String methodName, byte[] request)
            throws ApiProxyException {
        return baseDelegate.makeSyncCall(environment, packageName, methodName, request);
    }

    @Override
    public Future<byte[]> makeAsyncCall(Environment environment, String packageName, String methodName, byte[] request,
                                        ApiConfig apiConfig) {
        return baseDelegate.makeAsyncCall(environment, packageName, methodName, request, apiConfig);
    }

    @Override
    public void log(Environment environment, LogRecord logRecord) {
        baseDelegate.log(environment, logRecord);
    }

    @Override
    public void flushLogs(Environment environment) {
        baseDelegate.flushLogs(environment);
    }

    @Override
    public List<Thread> getRequestThreads(Environment environment) {
        return baseDelegate.getRequestThreads(environment);
    }
    
    public Delegate<Environment> getBaseDelegate() {
        return baseDelegate;
    }    
}
