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
import com.google.appengine.labs.repackaged.org.json.CDL;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
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
    public String exportKindToCsv(String kind) throws JSONException {
        String jsonData = exportKindToJson(kind);

        return convertToCsv(jsonData);
    }

    private String convertToCsv(String jsonData) throws JSONException {
        JSONObject output = new JSONObject(jsonData);
        JSONArray docs = output.getJSONArray("infile");

        return CDL.toString(docs);
    }
}
