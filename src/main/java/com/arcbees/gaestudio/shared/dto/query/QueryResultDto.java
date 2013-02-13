/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.shared.dto.query;

import java.io.Serializable;

public class QueryResultDto implements Serializable {

    private static final long serialVersionUID = -3499363771373323522L;
    
    private Integer resultSize;
    
    private Integer serializedSize;

    @SuppressWarnings("unused")
    protected QueryResultDto() {
    }
    
    public QueryResultDto(Integer resultSize, Integer serializedSize) {
        this.resultSize = resultSize;
        this.serializedSize = serializedSize;
    }

    public Integer getResultSize() {
        return resultSize;
    }

    public Integer getSerializedSize() {
        return serializedSize;
    }

}
