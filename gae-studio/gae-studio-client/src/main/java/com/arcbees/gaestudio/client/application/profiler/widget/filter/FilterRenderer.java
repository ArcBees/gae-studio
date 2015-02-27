/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
    FilterRenderer(
            FilterTemplate template,
            AppResources resources) {
        this.template = template;
        this.resources = resources;
    }

    @Override
    public String render(Filter value) {
        return template.filterTemplate(value.toString(), resources.styles().filterText()).asString();
    }
}
