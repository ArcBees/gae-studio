/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dispatch;

import java.util.logging.Logger;

import com.arcbees.gaestudio.shared.dispatch.DeleteEntityAction;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteEntityHandler extends AbstractActionHandler<DeleteEntityAction, DeleteEntityResult> {
    @Inject
    public DeleteEntityHandler(final Logger logger) {
        super(DeleteEntityAction.class);
    }

    @Override
    public DeleteEntityResult execute(DeleteEntityAction action, ExecutionContext context) throws ActionException {
        DispatchHelper.disableApiHooks();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        EntityDto entityDTO = action.getEntityDTO();
        KeyDto keyDTO = entityDTO.getKey();
        Key key = KeyFactory.createKey(keyDTO.getKind(), keyDTO.getId());

        datastore.delete(key);

        return new DeleteEntityResult();
    }

    @Override
    public void undo(DeleteEntityAction action, DeleteEntityResult result, ExecutionContext context)
            throws ActionException {
        // Nothing to do here
    }
}
