/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
    PanelToggle(AppResources appResources,
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
