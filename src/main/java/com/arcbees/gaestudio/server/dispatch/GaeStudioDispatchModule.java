/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.*;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class GaeStudioDispatchModule extends HandlerModule {

    @Override
    protected void configureHandlers() {
        bindHandler(GetNewDbOperationRecordsAction.class, GetNewDbOperationRecordsHandler.class);
        bindHandler(GetEntityKindsAction.class, GetEntityKindsHandler.class);
        bindHandler(GetEntitiesByKindAction.class, GetEntitiesByKindHandler.class);
        bindHandler(GetEntityCountByKindAction.class, GetEntityCountByKindHandler.class);
        bindHandler(SetRecordingAction.class, SetRecordingHandler.class);
    }

}
