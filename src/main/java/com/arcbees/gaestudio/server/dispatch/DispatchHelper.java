package com.arcbees.gaestudio.server.dispatch;

import com.google.apphosting.api.ApiProxy;

// TODO externalize magic strings
public class DispatchHelper {
    
    @SuppressWarnings("unused")
    private DispatchHelper() {
    }

    /**
     * Disable API hooks for the current request.  This is useful to prevent activity originated
     * by GAE Studio itself from showing in the profiler.
     */
    public static void disableApiHooks() {
        ApiProxy.getCurrentEnvironment().getAttributes().put("GaeStudio.disableApiHooks", true);
    }

}
