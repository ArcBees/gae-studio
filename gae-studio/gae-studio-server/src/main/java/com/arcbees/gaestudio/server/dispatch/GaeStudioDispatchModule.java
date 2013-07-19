/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dispatch;

import java.util.UUID;

import javax.inject.Singleton;

import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesAction;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityAction;
import com.arcbees.gaestudio.shared.dispatch.GetEmptyKindEntityAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityDtoAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityKindsAction;
import com.arcbees.gaestudio.shared.dispatch.GetNewDbOperationRecordsAction;
import com.arcbees.gaestudio.shared.dispatch.SetRecordingAction;
import com.arcbees.gaestudio.shared.dispatch.UpdateEntityAction;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.inject.Provides;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class GaeStudioDispatchModule extends HandlerModule {
    // TODO: Generate this only once per application through a propertie file.
    private static final String CLIENT_ID = UUID.randomUUID().toString();
    private static final String TRACKING_CODE = "UA-41550930-4";
    private static final String APPLICATION_NAME = "GAE-Studio";
    private static final String APPLICATION_VERSION = "1.0";

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
        bindHandler(DeleteEntitiesAction.class, DeleteEntitiesHandler.class);
    }

    @Provides
    @Singleton
    GoogleAnalytic createGoogleAnalytic() {
        GoogleAnalytic googleAnalytic
                = GoogleAnalytic.build(CLIENT_ID, TRACKING_CODE, APPLICATION_NAME, APPLICATION_VERSION);

        googleAnalytic.trackEvent(GaConstants.CAT_INITIALIZATION, GaConstants.APPLICATION_LOADED);

        return googleAnalytic;
    }
}
