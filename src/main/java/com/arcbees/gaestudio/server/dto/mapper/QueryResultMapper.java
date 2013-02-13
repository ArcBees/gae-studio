/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.server.dto.mapper;

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
