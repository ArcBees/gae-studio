/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto.query;

import java.io.Serializable;

public class QueryResultDto implements Serializable {
    private Integer resultSize;
    private Integer serializedSize;

    @SuppressWarnings("unused")
    public QueryResultDto() {
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

    public void setResultSize(Integer resultSize) {
        this.resultSize = resultSize;
    }

    public void setSerializedSize(Integer serializedSize) {
        this.serializedSize = serializedSize;
    }
}
