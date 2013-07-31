/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.dispatch;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.DatastoreHelper;
import com.arcbees.gaestudio.server.GaConstants;
import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.shared.dispatch.GetEntityDtoAction;
import com.arcbees.gaestudio.shared.dispatch.GetEntityDtoResult;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.arcbees.googleanalytic.GoogleAnalytic;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class GetEntityDtoHandler extends AbstractActionHandler<GetEntityDtoAction, GetEntityDtoResult> {
    private static final String GET_ENTITY_DTO = "Get Entity Dto";

    private final GoogleAnalytic googleAnalytic;
    private final DatastoreHelper datastoreHelper;

    @Inject
    GetEntityDtoHandler(GoogleAnalytic googleAnalytic,
                        DatastoreHelper datastoreHelper) {
        super(GetEntityDtoAction.class);

        this.googleAnalytic = googleAnalytic;
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public GetEntityDtoResult execute(GetEntityDtoAction action,
                                      ExecutionContext context) throws ActionException {
        googleAnalytic.trackEvent(GaConstants.CAT_SERVER_CALL, GET_ENTITY_DTO);

        KeyDto keyDto = action.getKeyDto();

        Entity entity;
        try {
            entity = datastoreHelper.get(keyDto);
        } catch (EntityNotFoundException e) {
            throw new ActionException(e);
        }

        GetEntityDtoResult result = new GetEntityDtoResult();
        EntityDto entityDto = EntityMapper.mapDTO(entity);
        result.setEntityDto(entityDto);

        return result;
    }
}
