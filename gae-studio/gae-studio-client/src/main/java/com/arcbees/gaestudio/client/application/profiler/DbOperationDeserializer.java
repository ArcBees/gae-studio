/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.profiler.jsonmappers.DeleteOperationMapper;
import com.arcbees.gaestudio.client.application.profiler.jsonmappers.GetOperationMapper;
import com.arcbees.gaestudio.client.application.profiler.jsonmappers.PutOperationMapper;
import com.arcbees.gaestudio.client.application.profiler.jsonmappers.QueryOperationMapper;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.dto.OperationKind;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class DbOperationDeserializer {
    private final GetOperationMapper getOperationMapper;
    private final PutOperationMapper putOperationMapper;
    private final DeleteOperationMapper deleteOperationMapper;
    private final QueryOperationMapper queryOperationMapper;

    @Inject
    DbOperationDeserializer(GetOperationMapper getOperationMapper,
                            PutOperationMapper putOperationMapper,
                            DeleteOperationMapper deleteOperationMapper,
                            QueryOperationMapper queryOperationMapper) {
        this.getOperationMapper = getOperationMapper;
        this.putOperationMapper = putOperationMapper;
        this.deleteOperationMapper = deleteOperationMapper;
        this.queryOperationMapper = queryOperationMapper;
    }

    public DbOperationRecordDto deserialize(String json) {
        JSONValue jsonValue = JSONParser.parseStrict(json);
        JSONObject jsonObject = jsonValue.isObject();
        String kind = jsonObject.get("type").isString().stringValue();
        OperationKind operationKind = OperationKind.valueOf(kind);

        return deserialize(json, operationKind);
    }

    private DbOperationRecordDto deserialize(String json, OperationKind operationKind) {
        DbOperationRecordDto recordDto;

        if (operationKind.equals(OperationKind.DELETE)) {
            recordDto = deleteOperationMapper.read(json);
        } else if (operationKind.equals(OperationKind.PUT)) {
            recordDto = putOperationMapper.read(json);
        } else if (operationKind.equals(OperationKind.GET)) {
            recordDto = getOperationMapper.read(json);
        } else {
            recordDto = queryOperationMapper.read(json);
        }

        return recordDto;
    }
}
