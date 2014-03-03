/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.FormPanel;

public class LoginFormHelper {
    private static final String LOGINFORM_ID = "loginForm";
    private static final String LOGINBUTTON_ID = "loginSubmit";
    private static final String EMAIL_ID = "loginEmail";
    private static final String PASSWORD_ID = "loginPassword";
    private static final String REGISTER_LINK_ID = "registerLink";
    private static final String FORGOT_LINK_ID = "forgotLink";

    private final FormPanel form;

    @Inject
    LoginFormHelper(AppResources appResources) {
        form = FormPanel.wrap(Document.get().getElementById(LOGINFORM_ID), false);
        form.setAction("javascript:__gwt_login(0)");

        getSubmitButton().addClassName(appResources.styles().bigBlueBtn());
    }

    public FormPanel getLoginFormPanel() {
        return form;
    }

    public ButtonElement getSubmitButton() {
        return (ButtonElement) Document.get().getElementById(LOGINBUTTON_ID);
    }

    public String getPassword() {
        return getPasswordElement().getValue();
    }

    public String getUsername() {
        return getUsernameElement().getValue();
    }

    public AnchorElement getRegisterLinkElement() {
        return ((AnchorElement) Document.get().getElementById(REGISTER_LINK_ID));
    }

    public AnchorElement getForgotLinkElement() {
        return ((AnchorElement) Document.get().getElementById(FORGOT_LINK_ID));
    }

    public InputElement getUsernameElement() {
        return ((InputElement) Document.get().getElementById(EMAIL_ID));
    }

    public InputElement getPasswordElement() {
        return ((InputElement) Document.get().getElementById(PASSWORD_ID));
    }
}
