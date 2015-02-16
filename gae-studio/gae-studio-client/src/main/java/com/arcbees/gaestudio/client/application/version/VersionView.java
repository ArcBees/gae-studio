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

package com.arcbees.gaestudio.client.application.version;

import javax.inject.Inject;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class VersionView extends ViewImpl implements VersionPresenter.MyView {
    interface Binder extends UiBinder<Widget, VersionView> {
    }

    interface VersionTemplate extends SafeHtmlTemplates {
        @SafeHtmlTemplates.Template("Version: {0} (<b>Latest version: {1}</b>)")
        SafeHtml thereIsANewerVersion(String version, String latestVersion);

        @SafeHtmlTemplates.Template("Version: {0} (Newest version)</b>")
        SafeHtml youHaveTheNewestVersion(String version);
    }

    @UiField
    InlineHTML label;

    private final VersionTemplate versionTemplate;

    @Inject
    VersionView(
            Binder binder,
            VersionTemplate versionTemplate) {
        this.versionTemplate = versionTemplate;

        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void setVersion(String version, String latestVersion) {
        SafeHtml versionHtml = versionTemplate.youHaveTheNewestVersion(version);

        if (!latestVersion.equals(version)) {
            versionHtml = versionTemplate.thereIsANewerVersion(version, latestVersion);
        }

        label.setHTML(versionHtml);
    }
}
