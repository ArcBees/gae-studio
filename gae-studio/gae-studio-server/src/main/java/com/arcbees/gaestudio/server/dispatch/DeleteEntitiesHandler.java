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
import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesAction;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesResult;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

import static com.google.appengine.api.datastore.Query.FilterOperator;
import static com.google.appengine.api.datastore.Query.FilterPredicate;

public class DeleteEntitiesHandler extends AbstractActionHandler<DeleteEntitiesAction, DeleteEntitiesResult> {
    private static final String DELETE_ENTITIES = "Delete Entities by ";

    private final GoogleAnalytic googleAnalytic;

    @Inject
    DeleteEntitiesHandler(GoogleAnalytic googleAnalytic) {
        super(DeleteEntitiesAction.class);

        this.googleAnalytic = googleAnalytic;
    }

    @Override
    public DeleteEntitiesResult execute(DeleteEntitiesAction action,
                                        ExecutionContext context) throws ActionException {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, getEvent(action));

        DispatchHelper.disableApiHooks();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        deleteEntities(datastore, action);

        return new DeleteEntitiesResult();
    }

    private void deleteEntities(DatastoreService datastore, DeleteEntitiesAction action) {
        switch (action.getDeleteEntitiesType()) {
            case KIND:
                deleteByKind(datastore, action.getValue());
                break;
            case NAMESPACE:
                deleteByNamespace(datastore, action.getValue());
                break;
            case ALL:
                deleteAll(datastore);
                break;
        }
    }

    private void deleteByNamespace(DatastoreService datastore, String value) {
        Query.Filter filter = new FilterPredicate(Entities.NAMESPACE_METADATA_KIND, FilterOperator.EQUAL, value);
        Query query = new Query().setFilter(filter);

        Iterable<Entity> entities = datastore.prepare(query).asIterable();

        deleteEntities(datastore, entities);
    }

    private void deleteByKind(DatastoreService datastore, String kind) {
        Iterable<Entity> entities = datastore.prepare(new Query(kind)).asIterable();

        deleteEntities(datastore, entities);
    }

    private void deleteEntities(DatastoreService datastore, Iterable<Entity> entities) {
        for (Entity entity : entities) {
            datastore.delete(entity.getKey());
        }
    }

    private void deleteAll(DatastoreService datastore) {
        Iterable<Entity> entities = datastore.prepare(new Query()).asIterable();
        deleteEntities(datastore, entities);
    }

    private String getEvent(DeleteEntitiesAction action) {
        return DELETE_ENTITIES + action.getDeleteEntitiesType().name();
    }
}
