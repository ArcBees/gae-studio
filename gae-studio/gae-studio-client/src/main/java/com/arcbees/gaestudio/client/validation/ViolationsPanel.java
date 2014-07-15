/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.validation;

import java.util.List;

import javax.validation.ConstraintViolation;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class ViolationsPanel implements IsWidget, HasVisibility {
    interface Binder extends UiBinder<HTML, ViolationsPanel> {
    }

    private static final Binder BINDER = GWT.create(Binder.class);

    private final HTML errorMessages;

    ViolationsPanel() {
        errorMessages = BINDER.createAndBindUi(this);
    }

    @Override
    public Widget asWidget() {
        return errorMessages;
    }

    public <T> void setViolations(Iterable<ConstraintViolation<T>> violations) {
        List<String> messages = Lists.newArrayList();

        for (ConstraintViolation<?> violation : violations) {
            String message = violation.getMessage();
            messages.add(message);
        }

        String html = "<p>" + Joiner.on("</p><p>").join(messages) + "</p>";

        errorMessages.setHTML(SafeHtmlUtils.fromTrustedString(html));
    }

    @Override
    public boolean isVisible() {
        return errorMessages.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        errorMessages.setVisible(visible);
    }
}
