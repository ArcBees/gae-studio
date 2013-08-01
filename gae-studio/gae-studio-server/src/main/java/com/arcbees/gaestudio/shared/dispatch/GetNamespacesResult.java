/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dispatch;

import java.util.List;

import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.gwtplatform.dispatch.shared.Result;

public class GetNamespacesResult implements Result {
    private List<AppIdNamespaceDto> namespaces;

    public GetNamespacesResult() {
    }

    public GetNamespacesResult(List<AppIdNamespaceDto> namespaces) {
        this.namespaces = namespaces;
    }

    public List<AppIdNamespaceDto> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(List<AppIdNamespaceDto> namespaces) {
        this.namespaces = namespaces;
    }
}
