/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dto.entity;

import com.google.gwt.user.client.rpc.IsSerializable;

public class KeyDto implements IsSerializable {
    private String kind;
    private Long id;
    private ParentKeyDto parentKeyDto;
    private AppIdNamespaceDto appIdNamespaceDto;

    @SuppressWarnings("unused")
    protected KeyDto() {
    }

    public KeyDto(String kind,
                  Long id,
                  ParentKeyDto parentKeyDto,
                  AppIdNamespaceDto appIdNamespaceDto) {
        this.kind = kind;
        this.id = id;
        this.parentKeyDto = parentKeyDto;
        this.appIdNamespaceDto = appIdNamespaceDto;
    }

    public String getKind() {
        return kind;
    }

    public Long getId() {
        return id;
    }

    public ParentKeyDto getParentKey() {
        return parentKeyDto;
    }

    public void setParentKey(ParentKeyDto parentKeyDto) {
        this.parentKeyDto = parentKeyDto;
    }

    public AppIdNamespaceDto getAppIdNamespaceDto() {
        return appIdNamespaceDto;
    }

    public void setAppIdNamespaceDto(AppIdNamespaceDto appIdNamespaceDto) {
        this.appIdNamespaceDto = appIdNamespaceDto;
    }
}
