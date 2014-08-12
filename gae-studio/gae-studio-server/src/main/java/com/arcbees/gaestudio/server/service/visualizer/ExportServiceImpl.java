/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.visualizer;

import java.util.List;

import javax.inject.Inject;

import org.json.JSONException;

import com.arcbees.gaestudio.server.util.JsonToCsvConverter;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.repackaged.com.google.common.base.Splitter;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
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
    public String exportToJson(String kind, String namespace, String encodedKeys) {
        Iterable<Entity> entities;
        if (Strings.isNullOrEmpty(encodedKeys)) {
            entities = entitiesService.getEntities(kind, namespace, null, null);
        } else {
            List<String> encodedKeysList = Splitter.on(",").splitToList(encodedKeys);
            List<Key> keys = Lists.transform(encodedKeysList, new Function<String, Key>() {
                @Override
                public Key apply(String input) {
                    return KeyFactory.stringToKey(input);
                }
            });

            entities = entitiesService.getEntities(keys);
        }

        return gson.toJson(entities);
    }

    @Override
    public String exportToCsv(String kind, String namespace, String encodedKeys) throws JSONException {
        String jsonData = exportToJson(kind, namespace, encodedKeys);

        return jsonToCsvConverter.convert(jsonData);
    }
}
