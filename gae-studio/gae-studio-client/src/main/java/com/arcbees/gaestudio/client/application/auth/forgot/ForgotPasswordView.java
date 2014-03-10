/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth.forgot;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.ui.AjaxLoader;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.google.common.base.Strings;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class ForgotPasswordView extends ViewWithUiHandlers<ForgotPasswordUiHandlers>
        implements ForgotPasswordPresenter.MyView {
    interface Binder extends UiBinder<Widget, ForgotPasswordView> {
    }

    @UiField
    TextBox email;
    @UiField
    Button submit;
    @UiField(provided = true)
    AjaxLoader ajaxLoader;
    @UiField
    DivElement errorMessage;

    private final AppConstants constants;

    @Inject
    ForgotPasswordView(Binder uiBinder,
                       AjaxLoader ajaxLoader,
                       AppConstants constants) {
        this.ajaxLoader = ajaxLoader;
        this.constants = constants;

        initWidget(uiBinder.createAndBindUi(this));

        $(email).attr("placeholder", "Email");
    }

    @Override
    public void resetForm() {
        resetSubmit();
        email.setText("");
    }

    @Override
    public void resetSubmit() {
        ajaxLoader.hide();
        submit.setEnabled(true);
        clearErrorMessage();
    }

    @UiHandler("submit")
    void onSubmitClicked(ClickEvent event) {
        doSubmit();
    }

    @UiHandler("email")
    void onEnter(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            doSubmit();
        }
    }

    private void doSubmit() {
        if (Strings.isNullOrEmpty(email.getText())) {
            showErrorMessage(constants.allFieldsAreRequired());
        } else {
            ajaxLoader.show();
            submit.setEnabled(false);

            getUiHandlers().forgotPassword(email.getText());
        }
    }

    private void showErrorMessage(String message) {
        errorMessage.setInnerText(message);
        errorMessage.getStyle().setDisplay(Style.Display.BLOCK);
    }

    private void clearErrorMessage() {
        errorMessage.getStyle().setDisplay(Style.Display.NONE);
        errorMessage.setInnerText("");
    }
}
