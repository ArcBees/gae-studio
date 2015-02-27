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

package com.arcbees.gaestudio.client.application.support;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import com.arcbees.analytics.shared.Analytics;
import com.arcbees.gaestudio.client.validation.ViolationsPanel;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

import static com.arcbees.gaestudio.client.application.analytics.EventCategories.UI_ELEMENTS;

public class SupportView extends PopupViewWithUiHandlers<SupportUiHandlers>
        implements SupportPresenter.MyView, Editor<SupportMessage> {
    interface Binder extends UiBinder<Widget, SupportView> {
    }

    interface Driver extends SimpleBeanEditorDriver<SupportMessage, SupportView> {
    }

    @UiField
    TextBox name;
    @UiField
    TextBox email;
    @UiField
    TextBox companyName;
    @UiField
    TextBox subject;
    @UiField
    TextArea body;
    @UiField
    ViolationsPanel violations;

    private final Driver driver;
    private final Analytics analytics;

    @Inject
    SupportView(
            EventBus eventBus,
            Binder binder,
            Driver driver,
            Analytics analytics) {
        super(eventBus);

        this.driver = driver;
        this.analytics = analytics;

        initWidget(binder.createAndBindUi(this));

        driver.initialize(this);
    }

    @Override
    public void edit(SupportMessage supportMessage) {
        driver.edit(supportMessage);
    }

    @Override
    public void showViolations(Set<ConstraintViolation<SupportMessage>> constraintViolations) {
        violations.setViolations(constraintViolations);
        violations.setVisible(true);
    }

    @Override
    public void hideViolations() {
        violations.setVisible(false);
    }

    @UiHandler("cancel")
    void onCancel(ClickEvent event) {
        hide();

        analytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Submit Issue Popup -> Close");
    }

    @UiHandler("send")
    void onSend(ClickEvent event) {
        getUiHandlers().send(driver.flush());

        analytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Submit Issue Popup -> Submit");
    }
}
