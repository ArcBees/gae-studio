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
