/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.i18n.client.LocalizableResource;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;

@LocalizableResource.DefaultLocale("en")
public interface AppMessages extends Messages {
    String requestWithHisNumber(Long number);

    SafeHtml deleteEntity(String kind, Long id);

    SafeHtml deleteEntitiesOfKind(String kind);

    SafeHtml deleteEntitiesOfNamespace(String namespace);

    SafeHtml deleteEntitiesOfDefaultNamespace();

    SafeHtml deleteEntitiesOfKindOfDefaultNamespace(String kind);

    SafeHtml deleteEntitiesOfKindOfNamespace(String kind, String namespace);

    SafeHtml deleteAllEntities();
}
