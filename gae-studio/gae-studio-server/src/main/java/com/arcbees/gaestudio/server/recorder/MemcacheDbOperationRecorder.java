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

package com.arcbees.gaestudio.server.recorder;

import java.util.Set;

import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.arcbees.gaestudio.server.channel.ChannelMessageSender;
import com.arcbees.gaestudio.server.channel.ClientService;
import com.arcbees.gaestudio.server.dto.mapper.QueryMapper;
import com.arcbees.gaestudio.server.dto.mapper.QueryResultMapper;
import com.arcbees.gaestudio.server.dto.mapper.StackTraceElementMapper;
import com.arcbees.gaestudio.shared.channel.Message;
import com.arcbees.gaestudio.shared.channel.Topic;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.dto.DeleteRecordDto;
import com.arcbees.gaestudio.shared.dto.GetRecordDto;
import com.arcbees.gaestudio.shared.dto.PutRecordDto;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDto;
import com.arcbees.gaestudio.shared.util.StackInspector;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.apphosting.api.DatastorePb;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class MemcacheDbOperationRecorder implements DbOperationRecorder {
    private final Provider<MemcacheService> memcacheServiceProvider;
    private final ChannelMessageSender channelMessageSender;
    private final Provider<Long> requestIdProvider;
    private final StackInspector stackInspector;
    private final Gson gson = new Gson();
    private final ClientService clientService;

    @Inject
    MemcacheDbOperationRecorder(
            Provider<MemcacheService> memcacheServiceProvider,
            ChannelMessageSender channelMessageSender,
            ClientService clientService,
            @Named(GaeStudioConstants.REQUEST_ID) Provider<Long> requestIdProvider,
            StackInspector stackInspector) {
        this.memcacheServiceProvider = memcacheServiceProvider;
        this.channelMessageSender = channelMessageSender;
        this.requestIdProvider = requestIdProvider;
        this.stackInspector = stackInspector;
        this.clientService = clientService;
    }

    @Override
    public void recordDbOperation(
            DatastorePb.DeleteRequest request,
            DatastorePb.DeleteResponse response,
            int executionTimeMs) {
        recordOperation(new DeleteRecordDto(
                // request, response,
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }

    @Override
    public void recordDbOperation(
            DatastorePb.GetRequest request,
            DatastorePb.GetResponse response,
            int executionTimeMs) {
        recordOperation(new GetRecordDto(
                // request, response,
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(),
                generateId(),
                executionTimeMs));
    }

    @Override
    public void recordDbOperation(
            DatastorePb.PutRequest request,
            DatastorePb.PutResponse response,
            int executionTimeMs) {
        recordOperation(new PutRecordDto(
                // request, response,
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(),
                generateId(),
                executionTimeMs));
    }

    @Override
    public void recordDbOperation(DatastorePb.Query query, DatastorePb.QueryResult queryResult, int executionTimeMs) {
        recordOperation(new QueryRecordDto(
                QueryMapper.mapDTO(query),
                QueryResultMapper.mapDTO(queryResult),
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(),
                generateId(),
                executionTimeMs));
    }

    private void recordOperation(DbOperationRecordDto record) {
        Set<String> connectedClients = clientService.getClientIds();

        if (!connectedClients.isEmpty()) {
            broadCastOperations(record, connectedClients);
        }
    }

    private long generateId() {
        return memcacheServiceProvider.get().increment(MemcacheKey.DB_OPERATION_COUNTER.getName(), 1L, 0L);
    }

    private void broadCastOperations(DbOperationRecordDto record, Set<String> connectedClients) {
        Class<?> clazz = record.getClass();
        String serializedRecord = gson.toJson(record, clazz);

        for (String clientId : connectedClients) {
            streamRecord(clientId, serializedRecord);
        }
    }

    private void streamRecord(String clientId, String serializedRecord) {
        channelMessageSender.sendMessage(clientId, new Message(Topic.DB_OPERATION, serializedRecord));
    }
}

