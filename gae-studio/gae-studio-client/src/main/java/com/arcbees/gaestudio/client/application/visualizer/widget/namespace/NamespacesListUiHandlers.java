/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.namespace;

import com.arcbees.gaestudio.client.dto.entity.AppIdNamespaceDto;
import com.gwtplatform.mvp.client.UiHandlers;

interface NamespacesListUiHandlers extends UiHandlers {
    void deleteAllFromNamespace(AppIdNamespaceDto namespaceDto);
}
