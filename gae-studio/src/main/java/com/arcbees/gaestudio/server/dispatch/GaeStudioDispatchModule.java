/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
        bindHandler(UpdateEntityAction.class, UpdateEntityHandler.class);
        bindHandler(GetEmptyKindEntityAction.class, GetEmptyKindEntityHandler.class);
        bindHandler(DeleteEntityAction.class, DeleteEntityHandler.class);
        bindHandler(GetEntityDtoAction.class, GetEntityDtoHandler.class);
    }
}
