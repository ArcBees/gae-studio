/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.google.apphosting.api.ApiProxy;

public class AppEngineHelper {
    @SuppressWarnings("unused")
    private AppEngineHelper() {
    }

    /**
     * Disable API hooks for the current request.  This is useful to prevent activity originated
     * by GAE Studio itself from showing in the profiler.
     */
    public static void disableApiHooks() {
        ApiProxy.getCurrentEnvironment().getAttributes().put(GaeStudioConstants.DISABLE_API_HOOKS, true);
    }
}
