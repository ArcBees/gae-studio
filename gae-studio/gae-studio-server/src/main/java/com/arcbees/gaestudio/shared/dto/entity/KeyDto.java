/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto.entity;

import java.io.Serializable;

public class KeyDto implements Serializable {
    private String kind;
    private Long id;
    private ParentKeyDto parentKeyDTO;

    @SuppressWarnings("unused")
    protected KeyDto() {
    }

    public KeyDto(String kind, Long id, ParentKeyDto parentKeyDTO) {
        this.kind = kind;
        this.id = id;
        this.parentKeyDTO = parentKeyDTO;
    }

    public String getKind() {
        return kind;
    }

    public Long getId() {
        return id;
    }

    public ParentKeyDto getParentKey() {
        return parentKeyDTO;
    }

    public void setParentKey(ParentKeyDto parentKeyDTO) {
        this.parentKeyDTO = parentKeyDTO;
    }
}
