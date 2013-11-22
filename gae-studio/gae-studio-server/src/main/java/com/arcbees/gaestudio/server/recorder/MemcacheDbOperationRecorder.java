/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.recorder;

import java.util.ArrayList;
import java.util.List;

import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.arcbees.gaestudio.server.dto.mapper.QueryMapper;
import com.arcbees.gaestudio.server.dto.mapper.QueryResultMapper;
import com.arcbees.gaestudio.server.dto.mapper.StackTraceElementMapper;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.dto.DeleteRecordDto;
import com.arcbees.gaestudio.shared.dto.GetRecordDto;
import com.arcbees.gaestudio.shared.dto.PutRecordDto;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDto;
import com.arcbees.gaestudio.shared.util.StackInspector;
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.apphosting.api.DatastorePb;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

// TODO externalize magic literals
public class MemcacheDbOperationRecorder implements DbOperationRecorder {
    private final Provider<MemcacheService> memcacheServiceProvider;
    private final Provider<Long> requestIdProvider;
    private final StackInspector stackInspector;
    private final ChannelService channelService = ChannelServiceFactory.getChannelService();
    private final Gson gson = new Gson();

    @Inject
    MemcacheDbOperationRecorder(Provider<MemcacheService> memcacheServiceProvider,
                                @Named("requestId") Provider<Long> requestIdProvider,
                                StackInspector stackInspector) {
        this.memcacheServiceProvider = memcacheServiceProvider;
        this.requestIdProvider = requestIdProvider;
        this.stackInspector = stackInspector;
    }

    @Override
    public void recordDbOperation(DatastorePb.DeleteRequest request, DatastorePb.DeleteResponse response,
                                  int executionTimeMs) {
        recordOperation(new DeleteRecordDto(
                //request, response,
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }

    @Override
    public void recordDbOperation(DatastorePb.GetRequest request, DatastorePb.GetResponse response,
                                  int executionTimeMs) {
        recordOperation(new GetRecordDto(
                //request, response,
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }

    @Override
    public void recordDbOperation(DatastorePb.PutRequest request, DatastorePb.PutResponse response,
                                  int executionTimeMs) {
        recordOperation(new PutRecordDto(
                //request, response,
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }

    @Override
    public void recordDbOperation(DatastorePb.Query query, DatastorePb.QueryResult queryResult, int executionTimeMs) {
        recordOperation(new QueryRecordDto(
                QueryMapper.mapDTO(query), QueryResultMapper.mapDTO(queryResult),
                StackTraceElementMapper.mapDTO(stackInspector.getCaller(Thread.currentThread().getStackTrace())),
                requestIdProvider.get(), generateId(), executionTimeMs));
    }

    private void recordOperation(DbOperationRecordDto record) {
        List<String> connectedClients = getConnectedClients();

        if (!connectedClients.isEmpty()) {
            broadCastOperations(record, connectedClients);
        }
    }

    private long generateId() {
        return memcacheServiceProvider.get().increment(MemcacheKey.DB_OPERATION_COUNTER.getName(), 1L, 0L);
    }

    private List<String> getConnectedClients() {
        MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();
        Object object =  memcacheService.get(GaeStudioConstants.GAESTUDIO_OPERATIONS_CLIENT_IDS);

        if(object == null) {
            return new ArrayList<String>();
        } else {
            return (List<String>)object;
        }
    }

    private void broadCastOperations(DbOperationRecordDto record, List<String> connectedClients) {
        Class<?> clazz = record.getClass();
        String serializedRecord = gson.toJson(record, clazz);

        for (String clientId : connectedClients) {
            streamRecord(clientId, serializedRecord);
        }
    }

    private void streamRecord(String clientId, String serializedRecord) {
        channelService.sendMessage(new ChannelMessage(clientId, serializedRecord));
    }
}

