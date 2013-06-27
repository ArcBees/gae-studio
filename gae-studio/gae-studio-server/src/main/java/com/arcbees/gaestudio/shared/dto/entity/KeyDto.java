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
    private ParentKeyDto parentKeyDTO;
    private AppIdNamespaceDto appIdNamespaceDTO;

    @SuppressWarnings("unused")
    protected KeyDto() {
    }

    public KeyDto(String kind,
                  Long id,
                  ParentKeyDto parentKeyDTO,
                  AppIdNamespaceDto appIdNamespaceDTO) {
        this.kind = kind;
        this.id = id;
        this.parentKeyDTO = parentKeyDTO;
        this.appIdNamespaceDTO = appIdNamespaceDTO;
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

    public AppIdNamespaceDto getAppIdNamespaceDTO() {
        return appIdNamespaceDTO;
    }

    public void setAppIdNamespaceDTO(AppIdNamespaceDto appIdNamespaceDTO) {
        this.appIdNamespaceDTO = appIdNamespaceDTO;
    }
}
