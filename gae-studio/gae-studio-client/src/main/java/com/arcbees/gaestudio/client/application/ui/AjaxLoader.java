/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
    AjaxLoader(AppResources appResources) {
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
}
