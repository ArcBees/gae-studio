/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dispatch;

import com.arcbees.gaestudio.server.DatastoreHelper;
import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesAction;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntitiesResult;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteEntitiesHandler extends AbstractActionHandler<DeleteEntitiesAction, DeleteEntitiesResult> {
    private static final String DELETE_ENTITIES = "Delete Entities by ";

    private final GoogleAnalytic googleAnalytic;
    private final DatastoreHelper datastoreHelper;

    @Inject
    DeleteEntitiesHandler(GoogleAnalytic googleAnalytic,
                          DatastoreHelper datastoreHelper) {
        super(DeleteEntitiesAction.class);

        this.googleAnalytic = googleAnalytic;
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public DeleteEntitiesResult execute(DeleteEntitiesAction action,
                                        ExecutionContext context) throws ActionException {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, getEvent(action));

        DispatchHelper.disableApiHooks();

        deleteEntities(action);

        return new DeleteEntitiesResult();
    }

    private void deleteEntities(DeleteEntitiesAction action) {
        switch (action.getDeleteEntitiesType()) {
            case KIND:
                deleteByKind(action.getKind());
                break;
            case NAMESPACE:
                deleteByNamespace(action.getKind());
                break;
            case KIND_NAMESPACE:
                deleteByKindAndNamespace(action.getKind(), action.getNamespace());
                break;
            case ALL:
                deleteAll();
                break;
        }
    }

    private void deleteByNamespace(String namespace) {
        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(namespace);

        Iterable<Entity> entities = getAllEntities();
        deleteEntities(entities);

        NamespaceManager.set(defaultNamespace);
    }

    private void deleteByKindAndNamespace(String kind, String namespace) {
        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(namespace);

        Iterable<Entity> entities = getAllEntitiesOfKind(kind);
        deleteEntities(entities);

        NamespaceManager.set(defaultNamespace);
    }

    private void deleteByKind(String kind) {
        Query query = new Query(kind).setKeysOnly();
        datastoreHelper.deleteOnAllNamespaces(query);
    }

    private void deleteEntities(Iterable<Entity> entities) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        for (Entity entity : entities) {
            datastore.delete(entity.getKey());
        }
    }

    private void deleteAll() {
        Iterable<Entity> entities = getAllEntitiesOfAllNamespaces();
        deleteEntities(entities);
    }

    private Iterable<Entity> getAllEntities() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        return datastore.prepare(new Query().setKeysOnly()).asIterable();
    }

    private Iterable<Entity> getAllEntitiesOfKind(String kind) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        return datastore.prepare(new Query(kind).setKeysOnly()).asIterable();
    }

    private Iterable<Entity> getAllEntitiesOfAllNamespaces() {
        return datastoreHelper.queryOnAllNamespaces(new Query().setKeysOnly());
    }

    private String getEvent(DeleteEntitiesAction action) {
        return DELETE_ENTITIES + action.getDeleteEntitiesType().name();
    }
}
