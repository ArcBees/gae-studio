/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto.mapper;

import com.arcbees.gaestudio.shared.dto.query.QueryResultDto;
import com.google.apphosting.api.DatastorePb;

public class QueryResultMapper {
    @SuppressWarnings("unused")
    private QueryResultMapper() {
    }

    public static QueryResultDto mapDTO(DatastorePb.QueryResult queryResult) {
        Integer resultSize = queryResult.resultSize();
        Integer serializedSize = queryResult.getSerializedSize();

        return new QueryResultDto(resultSize, serializedSize);
    }
}
