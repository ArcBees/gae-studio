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

package com.arcbees.gaestudio.client.application.ui;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class AjaxLoader implements IsWidget {
    private final Image widget;

    @Inject
    AjaxLoader(
            AppResources appResources) {
        this.widget = new Image();
        widget.setResource(appResources.ajaxLoader30px());

        hide();
    }

    public void show() {
        widget.setVisible(true);
    }

    public void hide() {
        widget.setVisible(false);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    public void setStyleName(String styleName) {
        asWidget().setStyleName(styleName);
    }
}
