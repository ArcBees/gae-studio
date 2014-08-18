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

import org.json.JSONException;

import com.arcbees.gaestudio.server.util.JsonToCsvConverter;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;

public class ExportServiceImpl implements ExportService {
    private final EntitiesService entitiesService;
    private final Gson gson;
    private final JsonToCsvConverter jsonToCsvConverter;

    @Inject
    ExportServiceImpl(EntitiesService entitiesService,
                      Gson gson,
                      JsonToCsvConverter jsonToCsvConverter) {
        this.entitiesService = entitiesService;
        this.gson = gson;
        this.jsonToCsvConverter = jsonToCsvConverter;
    }

    @Override
    public String exportKindToJson(String kind, String namespace) {
        Iterable<Entity> entities = entitiesService.getEntities(kind, namespace, null, null);

        return gson.toJson(entities);
    }

    @Override
    public String exportKindToCsv(String kind, String namespace) throws JSONException {
        String jsonData = exportKindToJson(kind, namespace);

        return jsonToCsvConverter.convert(jsonData);
    }
}
