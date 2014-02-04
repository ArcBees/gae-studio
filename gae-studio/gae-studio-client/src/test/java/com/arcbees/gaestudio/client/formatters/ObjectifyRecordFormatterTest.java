/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.formatters;

import java.util.ArrayList;

import javax.inject.Inject;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.shared.QueryFilterOperator;
import com.arcbees.gaestudio.shared.dto.query.QueryDto;
import com.arcbees.gaestudio.shared.dto.query.QueryFilterDto;
import com.arcbees.gaestudio.shared.dto.query.QueryOrderDto;
import com.arcbees.gaestudio.shared.dto.query.QueryRecordDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(JukitoRunner.class)
public class ObjectifyRecordFormatterTest {
    public final QueryFilterOperator ANY_OPERATOR = QueryFilterOperator.GREATER_THAN;

    @Inject
    ObjectifyRecordFormatter formatter;

    @Test
    public void formatRecord_filterValueContainsUnicodeReplacementCharacter_replacementCharacterIsEscaped() {
        //given
        QueryRecordDto queryRecordDto = getQueryRecordDto();
        QueryFilterDto queryFilterDto = getQueryFilterDto("theValue" + '\ufffd');
        queryRecordDto.getQuery().getFilters().add(queryFilterDto);

        //when
        String formatted = formatter.formatRecord(queryRecordDto);

        //then
        assertNotEquals("Formatted query still contains unicode character",
                "query().filter(\"null >\", theValue�)", formatted);
        assertEquals("Formatted query does not contain escaped unicode character",
                "query().filter(\"null >\", theValue\\ufffd)", formatted);
    }

    private QueryRecordDto getQueryRecordDto() {
        QueryRecordDto queryRecordDto = new QueryRecordDto();

        QueryDto queryDto = new QueryDto();
        queryDto.setFilters(new ArrayList<QueryFilterDto>());
        queryDto.setOrders(new ArrayList<QueryOrderDto>());

        queryRecordDto.setQuery(queryDto);

        return queryRecordDto;
    }

    private QueryFilterDto getQueryFilterDto(String value) {
        QueryFilterDto queryFilterDto = new QueryFilterDto();

        queryFilterDto.setOperator(ANY_OPERATOR);
        queryFilterDto.setValue(value);

        return queryFilterDto;
    }
}
