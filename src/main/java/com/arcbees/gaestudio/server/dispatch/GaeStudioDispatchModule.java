/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class GaeStudioDispatchModule extends HandlerModule {

    @Override
    protected void configureHandlers() {
        bindHandler(GetNewDbOperationRecordsAction.class, GetNewDbOperationRecordsHandler.class);
        bindHandler(GetEntityKindsAction.class, GetEntityKindsHandler.class);
        bindHandler(GetEntitiesByKindAction.class, GetEntitiesByKindHandler.class);
    }

}
