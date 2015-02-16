/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.profiler;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.recorder.MemcacheKey;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.anyCollection;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class OperationServiceImplTest {
    @Inject
    OperationServiceImpl operationService;
    @Inject
    MemcacheService memcacheService;

    @Test
    public void testGetMostRecentIdReturnNull() {
        // Given
        when(memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName())).thenReturn(null);

        // when
        Long mostRecentId = operationService.getMostRecentId();

        // then
        verify(memcacheService).get(MemcacheKey.DB_OPERATION_COUNTER.getName());
        assertNull(mostRecentId);
    }

    public void testGetMostRecentIdReturnNotNull() {
        // Given
        Long fromMemcache = 3L;
        when(memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName())).thenReturn(fromMemcache);

        // when
        Long mostRecentId = operationService.getMostRecentId();

        // then
        verify(memcacheService).get(MemcacheKey.DB_OPERATION_COUNTER.getName());
        assertEquals(fromMemcache, mostRecentId);
    }

    @Test
    public void testNoMostRecentIdGetOperationReturnNull() {
        // Given
        when(memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName())).thenReturn(null);

        // when
        List<DbOperationRecordDto> results = operationService.getOperations(0L, 1);

        // then
        verify(memcacheService).get(MemcacheKey.DB_OPERATION_COUNTER.getName());
        assertNull(results);
    }

    @Test
    public void testGetOperationLimitNullAndMostRecentIdEqualsLastId() {
        // Given
        Long lastId = 1L;
        when(memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName())).thenReturn(lastId);

        // when
        List<DbOperationRecordDto> results = operationService.getOperations(lastId, null);

        // then
        verify(memcacheService).get(MemcacheKey.DB_OPERATION_COUNTER.getName());
        assertNull(results);
    }

    @Test
    public void testGetOperationLimitNotNullAndMostRecentIdEqualsLastId() {
        // Given
        Long lastId = 1L;
        when(memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName())).thenReturn(lastId);

        // when
        List<DbOperationRecordDto> results = operationService.getOperations(lastId, 2);

        // then
        verify(memcacheService).get(MemcacheKey.DB_OPERATION_COUNTER.getName());
        assertNull(results);
    }

    @Test
    public void testGetOperationLimitNullAndMostRecentIdGreaterThanLastId(DbOperationRecordDto dtoFromMemCache) {
        // Given
        Long lastId = 1L;
        when(memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName())).thenReturn(2L);
        Map<String, Object> mapFromMemCache = Maps.newHashMap();
        mapFromMemCache.put("dummyKey", dtoFromMemCache);
        when(memcacheService.getAll(anyCollection())).thenReturn(mapFromMemCache);

        // when
        List<DbOperationRecordDto> results = operationService.getOperations(lastId, null);

        // then
        verify(memcacheService).getAll(Lists.newArrayList(MemcacheKey.DB_OPERATION_RECORD_PREFIX.getName() +
                2));
        verify(memcacheService).get(MemcacheKey.DB_OPERATION_COUNTER.getName());
        assertEquals(1, results.size());
        assertEquals(dtoFromMemCache, results.get(0));
    }

    @Test
    public void testGetOperationLimitNotNullAndMostRecentIdGreaterThanLastIdPlusLimit(
            DbOperationRecordDto dtoFromMemCache1,
            DbOperationRecordDto dtoFromMemCache2, DbOperationRecordDto dtoFromMemCache3) {
        // Given
        Long lastId = 1L;
        when(memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName())).thenReturn(5L);
        Map<String, Object> mapFromMemCache = Maps.newHashMap();
        mapFromMemCache.put("dummyKey1", dtoFromMemCache1);
        mapFromMemCache.put("dummyKey2", dtoFromMemCache2);
        mapFromMemCache.put("dummyKey3", dtoFromMemCache3);
        when(memcacheService.getAll(anyCollection())).thenReturn(mapFromMemCache);

        // when
        List<DbOperationRecordDto> results = operationService.getOperations(lastId, 3);

        // then
        verify(memcacheService).get(MemcacheKey.DB_OPERATION_COUNTER.getName());
        verify(memcacheService).getAll(Lists.newArrayList(MemcacheKey.DB_OPERATION_RECORD_PREFIX.getName() +
                2, MemcacheKey.DB_OPERATION_RECORD_PREFIX.getName() + 3,
                MemcacheKey.DB_OPERATION_RECORD_PREFIX.getName() +
                        4));
        assertEquals(3, results.size());
        assertEquals(dtoFromMemCache1, results.get(0));
        assertEquals(dtoFromMemCache2, results.get(1));
        assertEquals(dtoFromMemCache3, results.get(2));
    }

    @Test
    public void testGetOperationLimitNotNullAndMostRecentIdLessThanLastIdPlusLimit(
            DbOperationRecordDto dtoFromMemCache) {
        // Given
        Long lastId = 1L;
        when(memcacheService.get(MemcacheKey.DB_OPERATION_COUNTER.getName())).thenReturn(2L);
        Map<String, Object> mapFromMemCache = Maps.newHashMap();
        mapFromMemCache.put("dummyKey", dtoFromMemCache);
        when(memcacheService.getAll(anyCollection())).thenReturn(mapFromMemCache);

        // when
        List<DbOperationRecordDto> results = operationService.getOperations(lastId, 3);

        // then
        verify(memcacheService).get(MemcacheKey.DB_OPERATION_COUNTER.getName());
        verify(memcacheService).getAll(Lists.newArrayList(MemcacheKey.DB_OPERATION_RECORD_PREFIX.getName() +
                2));
        assertEquals(1, results.size());
        assertEquals(dtoFromMemCache, results.get(0));
    }
}
