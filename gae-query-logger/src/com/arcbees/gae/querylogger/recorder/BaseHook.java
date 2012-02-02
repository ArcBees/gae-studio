/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gae.querylogger.recorder;

import com.google.apphosting.api.ApiProxy.ApiConfig;
import com.google.apphosting.api.ApiProxy.ApiProxyException;
import com.google.apphosting.api.ApiProxy.Delegate;
import com.google.apphosting.api.ApiProxy.Environment;
import com.google.apphosting.api.ApiProxy.LogRecord;

import java.util.List;
import java.util.concurrent.Future;

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
