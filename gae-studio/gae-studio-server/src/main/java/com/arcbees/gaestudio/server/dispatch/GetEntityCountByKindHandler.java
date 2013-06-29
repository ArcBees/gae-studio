/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityCountByKindResult;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetEntityCountByKindHandler
        extends AbstractActionHandler<GetEntityCountByKindAction, GetEntityCountByKindResult> {
    private final GoogleAnalytic googleAnalytic;

    @Inject
    GetEntityCountByKindHandler(GoogleAnalytic googleAnalytic) {
        super(GetEntityCountByKindAction.class);

        this.googleAnalytic = googleAnalytic;
    }

    @Override
    public GetEntityCountByKindResult execute(GetEntityCountByKindAction action,
                                              ExecutionContext context) throws ActionException {
        googleAnalytic.trackEvent("Server Call", "Get Entity Count By Kind");

        DispatchHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query(action.getKind());
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        Integer count = datastore.prepare(query).countEntities(fetchOptions);

        return new GetEntityCountByKindResult(count);
    }
}
