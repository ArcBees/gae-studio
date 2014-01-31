/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.text.shared.AbstractRenderer;

public class FilterRenderer extends AbstractRenderer<Filter> {
    interface FilterTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("<span class=\"{1}\">View By</span> {0}")
        SafeHtml filterTemplate(String filterName, String spanClass);
    }

    private final FilterTemplate template;
    private final AppResources resources;

    @Inject
    FilterRenderer(FilterTemplate template,
                   AppResources resources) {
        this.template = template;
        this.resources = resources;
    }

    @Override
    public String render(Filter value) {
        return template.filterTemplate(value.toString(), resources.styles().filterText()).asString();
    }
}
