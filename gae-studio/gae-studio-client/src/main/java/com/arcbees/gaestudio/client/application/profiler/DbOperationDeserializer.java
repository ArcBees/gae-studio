/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
    DbOperationDeserializer(
            GetOperationMapper getOperationMapper,
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
