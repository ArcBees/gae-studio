package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.dispatch.GetNewDbOperationRecordsHandler;
import com.arcbees.gaestudio.server.domain.Sprocket;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.googlecode.objectify.ObjectifyService;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class ServerModule extends HandlerModule {

    @Override
    protected void configureHandlers() {
        ObjectifyService.register(Sprocket.class);

        bindHandler(GetNewDbOperationRecordsAction.class, GetNewDbOperationRecordsHandler.class);
    }

}
