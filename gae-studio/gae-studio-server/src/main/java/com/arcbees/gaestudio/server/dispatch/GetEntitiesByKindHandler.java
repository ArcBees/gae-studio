/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dispatch;

import java.util.ArrayList;
import java.util.List;

import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntitiesByKindResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

// TODO add logging
public class GetEntitiesByKindHandler extends AbstractActionHandler<GetEntitiesByKindAction, GetEntitiesByKindResult> {
    private static final String GET_ENTITIES_BY_KIND = "Get Entities By Kind";

    private final GoogleAnalytic googleAnalytic;

    @Inject
    GetEntitiesByKindHandler(GoogleAnalytic googleAnalytic) {
        super(GetEntitiesByKindAction.class);

        this.googleAnalytic = googleAnalytic;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GetEntitiesByKindResult execute(GetEntitiesByKindAction action,
                                           ExecutionContext context) throws ActionException {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_ENTITIES_BY_KIND);

        DispatchHelper.disableApiHooks();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        if (action.getOffset() != null) {
            fetchOptions.offset(action.getOffset());
        }
        if (action.getLimit() != null) {
            fetchOptions.limit(action.getLimit());
        }

        Query query = new Query(action.getKind());
        List<com.google.appengine.api.datastore.Entity> results = datastore.prepare(query).asList(fetchOptions);

        ArrayList<EntityDto> entities = new ArrayList<EntityDto>(results.size());
        for (com.google.appengine.api.datastore.Entity dbEntity : results) {
            entities.add(EntityMapper.mapDTO(dbEntity));
        }

        return new GetEntitiesByKindResult(entities);
    }
}
