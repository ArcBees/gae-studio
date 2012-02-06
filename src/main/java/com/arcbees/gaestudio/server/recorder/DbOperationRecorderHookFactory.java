/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.recorder;

import com.google.apphosting.api.ApiProxy;

public interface DbOperationRecorderHookFactory {

    public DbOperationRecorderHook create(ApiProxy.Delegate<ApiProxy.Environment> baseDelegate);

}
