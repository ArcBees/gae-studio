/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.visualizer;

import javax.inject.Inject;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;

public class ExportServiceImpl implements ExportService {
    private final EntitiesService entitiesService;
    private final Gson gson;

    @Inject
    ExportServiceImpl(EntitiesService entitiesService,
                      Gson gson) {
        this.entitiesService = entitiesService;
        this.gson = gson;
    }

    @Override
    public String exportKindToJson(String kind) {
        Iterable<Entity> entities = entitiesService.getEntities(kind, null, null);

        return gson.toJson(entities);
    }

    @Override
    public String exportKindToCsv(String kind) {
        Iterable<Entity> entities = entitiesService.getEntities(kind, null, null);

        return convertToCsv(entities);
    }

    private String convertToCsv(Iterable<Entity> entities) {
        //TODO : Convert to CSV
        return null;
    }
}
