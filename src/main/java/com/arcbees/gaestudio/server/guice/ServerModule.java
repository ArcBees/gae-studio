package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.dispatch.GetEntitiesByKindHandler;
import com.arcbees.gaestudio.server.dispatch.GetEntityKindsHandler;
import com.arcbees.gaestudio.server.dispatch.GetNewDbOperationRecordsHandler;
import com.arcbees.gaestudio.server.domain.Sprocket;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.googlecode.objectify.ObjectifyService;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class ServerModule extends HandlerModule {

    @Override
    protected void configureHandlers() {
        ObjectifyService.register(Sprocket.class);

        bindHandler(GetNewDbOperationRecordsAction.class, GetNewDbOperationRecordsHandler.class);
        bindHandler(GetEntityKindsAction.class, GetEntityKindsHandler.class);
        bindHandler(GetEntitiesByKindAction.class, GetEntitiesByKindHandler.class);
    }

}
