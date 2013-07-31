/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.shared.dispatch.GetNamespacesAction;
import com.arcbees.gaestudio.shared.dispatch.GetNamespacesResult;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.api.client.util.Lists;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetNamespacesHandler extends AbstractActionHandler<GetNamespacesAction, GetNamespacesResult> {
    private static final String GET_NAMESPACES = "Get Namespaces";

    private final GoogleAnalytic googleAnalytic;

    @Inject
    GetNamespacesHandler(GoogleAnalytic googleAnalytic) {
        super(GetNamespacesAction.class);

        this.googleAnalytic = googleAnalytic;
    }

    @Override
    public GetNamespacesResult execute(GetNamespacesAction action,
                                       ExecutionContext context) throws ActionException {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_NAMESPACES);

        DispatchHelper.disableApiHooks();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query(Entities.NAMESPACE_METADATA_KIND);
        Iterable<Entity> entities = datastore.prepare(query).asIterable();

        Iterable<AppIdNamespaceDto> namespaces = FluentIterable.from(entities)
                .transform(new Function<Entity, AppIdNamespaceDto>() {
                    @Override
                    public AppIdNamespaceDto apply(Entity input) {
                        return new AppIdNamespaceDto(input.getAppId(), input.getKey().getName());
                    }
                });

        return new GetNamespacesResult(Lists.newArrayList(namespaces));
    }
}
