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
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityAction;
import com.arcbees.gaestudio.shared.dispatch.DeleteEntityResult;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteEntityHandler extends AbstractActionHandler<DeleteEntityAction, DeleteEntityResult> {
    private static final String DELETE_ENTITY = "Delete Entity";

    private final GoogleAnalytic googleAnalytic;

    @Inject
    DeleteEntityHandler(GoogleAnalytic googleAnalytic) {
        super(DeleteEntityAction.class);

        this.googleAnalytic = googleAnalytic;
    }

    @Override
    public DeleteEntityResult execute(DeleteEntityAction action,
                                      ExecutionContext context) throws ActionException {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, DELETE_ENTITY);

        DispatchHelper.disableApiHooks();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        EntityDto entityDTO = action.getEntityDTO();
        KeyDto keyDTO = entityDTO.getKey();
        Key key = KeyFactory.createKey(keyDTO.getKind(), keyDTO.getId());

        datastore.delete(key);

        return new DeleteEntityResult();
    }

    @Override
    public void undo(DeleteEntityAction action,
                     DeleteEntityResult result,
                     ExecutionContext context)
            throws ActionException {
        // Nothing to do here
    }
}
