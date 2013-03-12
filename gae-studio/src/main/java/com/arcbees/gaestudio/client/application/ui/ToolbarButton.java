/*
 * Copyright 2013 ArcBees Inc.
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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

public class ToolbarButton extends Composite {
    public interface Binder extends UiBinder<Widget, ToolbarButton> {
    }

    @UiField(provided = true)
    AppResources resources;
    @UiField
    HTMLPanel button;
    @UiField
    Image image;
    @UiField
    InlineLabel text;

    @Inject
    public ToolbarButton(final Binder binder, final AppResources resources, @Assisted final String text,
                         @Assisted final ImageResource imageResource, @Assisted final ToolbarButtonCallback callback) {
        this.resources = resources;

        initWidget(binder.createAndBindUi(this));

        this.text.setText(text);
        this.image.setResource(imageResource);

        button.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                callback.onClicked();
            }
        }, ClickEvent.getType());
    }

    public void setEnabled(Boolean enabled) {
        if (enabled) {
            button.removeStyleName(resources.styles().toolbarButtonDisabled());
        } else {
            button.addStyleName(resources.styles().toolbarButtonDisabled());
        }
    }
}
