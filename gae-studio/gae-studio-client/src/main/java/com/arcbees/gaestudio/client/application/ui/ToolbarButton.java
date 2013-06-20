/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
    interface Binder extends UiBinder<Widget, ToolbarButton> {
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
    ToolbarButton(Binder binder,
                  AppResources resources,
                  @Assisted String text,
                  @Assisted ImageResource imageResource,
                  @Assisted final ToolbarButtonCallback callback) {
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
