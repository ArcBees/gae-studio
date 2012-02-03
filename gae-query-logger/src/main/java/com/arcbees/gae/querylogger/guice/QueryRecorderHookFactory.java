/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gae.querylogger.guice;

import com.arcbees.gae.querylogger.recorder.QueryRecorderHook;
import com.google.apphosting.api.ApiProxy;

public interface QueryRecorderHookFactory {

    public QueryRecorderHook create(ApiProxy.Delegate<ApiProxy.Environment> baseDelegate);

}
