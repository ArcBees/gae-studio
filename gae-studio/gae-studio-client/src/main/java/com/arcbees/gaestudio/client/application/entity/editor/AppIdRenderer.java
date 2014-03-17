/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.gwt.text.shared.AbstractRenderer;

class AppIdRenderer extends AbstractRenderer<AppIdNamespaceDto> {
    private final NamespaceTemplate template;

    @Inject
    AppIdRenderer(NamespaceTemplate template) {
        this.template = template;
    }

    @Override
    public String render(AppIdNamespaceDto object) {
        String value;
        if (object == null) {
            value = "<null>";
        } else {
            value = object.getAppId();
        }

        return template.namespaceTemplate(value).asString();
    }
}
