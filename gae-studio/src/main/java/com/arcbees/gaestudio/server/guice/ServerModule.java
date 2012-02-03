package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.dispatch.GenerateSampleDataHandler;
import com.arcbees.gaestudio.server.dispatch.GetEntitiesOfAKindHandler;
import com.arcbees.gaestudio.server.dispatch.GetEntityKindsHandler;
import com.arcbees.gaestudio.shared.dispatch.GenerateSampleDataAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesOfAKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.domain.Sprocket;
import com.googlecode.objectify.ObjectifyService;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class ServerModule extends HandlerModule {

    @Override
    protected void configureHandlers() {
        ObjectifyService.register(Sprocket.class);

        bindHandler(GenerateSampleDataAction.class, GenerateSampleDataHandler.class);
        bindHandler(GetEntitiesOfAKindAction.class, GetEntitiesOfAKindHandler.class);
        bindHandler(GetEntityKindsAction.class, GetEntityKindsHandler.class);
    }

}
