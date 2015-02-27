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
    public static final QueryFilterOperator ANY_OPERATOR = QueryFilterOperator.GREATER_THAN;

    @Inject
    ObjectifyRecordFormatter formatter;

    @Test
    public void formatRecord_filterValueContainsUnicodeReplacementCharacter_replacementCharacterIsEscaped() {
        // given
        QueryRecordDto queryRecordDto = getQueryRecordDto();
        QueryFilterDto queryFilterDto = getQueryFilterDto("theValue" + '\ufffd');
        queryRecordDto.getQuery().getFilters().add(queryFilterDto);

        // when
        String formatted = formatter.formatRecord(queryRecordDto);

        // then
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
