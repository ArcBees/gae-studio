package com.arcbees.gaestudio.server.dispatch;

import com.google.apphosting.api.ApiProxy;

// TODO externalize magic strings
public class DispatchHelper {
    
    @SuppressWarnings("unused")
    private DispatchHelper() {
    }
    
    public static void disableApiHooks() {
        ApiProxy.getCurrentEnvironment().getAttributes().put("GaeStudio.disableApiHooks", true);
    }

}
