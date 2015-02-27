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

package com.arcbees.gaestudio.client.ui;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.google.gwt.query.client.GQuery.$;

public class PanelToggle implements ClickHandler, IsWidget {
    public interface ToggleHandler {
        void onToggle();
    }

    private final ToggleHandler toggleHandler;
    private final Image widget;
    private final String flipped;

    @Inject
    PanelToggle(
            AppResources appResources,
            @Assisted ToggleHandler toggleHandler) {
        this.toggleHandler = toggleHandler;

        widget = new Image();

        widget.setResource(appResources.closeToggle());
        widget.addStyleName(appResources.styles().panelToggle());
        widget.addClickHandler(this);

        flipped = appResources.styles().flipped();
    }

    public void setAddStyleNames(String stylename) {
        widget.addStyleName(stylename);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void onClick(ClickEvent event) {
        toggleHandler.onToggle();
        rotateToggle();
    }

    public boolean isOpen() {
        return !$(widget).hasClass(flipped);
    }

    private void rotateToggle() {
        $(widget).toggleClass(flipped);
    }
}
