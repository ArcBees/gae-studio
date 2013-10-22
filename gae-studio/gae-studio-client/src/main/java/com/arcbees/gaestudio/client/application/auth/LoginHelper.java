/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.FormPanel;

public class LoginHelper {
    private static final String LOGINFORM_ID = "loginForm";
    private static final String LOGINBUTTON_ID = "loginSubmit";
    private static final String EMAIL_ID = "loginEmail";
    private static final String PASSWORD_ID = "loginPassword";
    private static final String REGISTER_LINK_ID = "registerLink";

    private final FormPanel form;

    LoginHelper() {
        form = FormPanel.wrap(Document.get().getElementById(LOGINFORM_ID), false);
        form.setAction("javascript:__gwt_login(0)");
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

    public void setUsername(String text) {
        getUsernameElement().setValue(text);
    }

    public void setPassword(String text) {
        getPasswordElement().setValue(text);
    }

    public void addStyleNameToRegisterLink(String style) {
        getRegisterLinkElement().addClassName(style);
    }

    public void addStyleNameToEmail(String style) {
        getUsernameElement().addClassName(style);
    }

    public void addStyleNameToPassword(String style) {
        getPasswordElement().addClassName(style);
    }

    public void removeStyleNameFromEmail(String style) {
        getUsernameElement().removeClassName(style);
    }

    public void removeStyleNameToRegisterLink(String style) {
        getRegisterLinkElement().removeClassName(style);
    }

    public void removeStyleNameFromPassword(String style) {
        getPasswordElement().removeClassName(style);
    }

    public AnchorElement getRegisterLinkElement() {
        return ((AnchorElement) Document.get().getElementById(REGISTER_LINK_ID));
    }

    private InputElement getUsernameElement() {
        return ((InputElement) Document.get().getElementById(EMAIL_ID));
    }

    private InputElement getPasswordElement() {
        return ((InputElement) Document.get().getElementById(PASSWORD_ID));
    }
}
