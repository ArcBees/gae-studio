/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto.entity;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ParentKeyDto implements IsSerializable {
    private String kind;
    private Long id;

    @SuppressWarnings("unused")
    protected ParentKeyDto() {
    }

    @JsonCreator
    public ParentKeyDto(@JsonProperty("kind") String kind,
                        @JsonProperty("id") Long id) {
        this.kind = kind;
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public Long getId() {
        return id;
    }
}
