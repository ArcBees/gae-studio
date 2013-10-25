/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.parseJsonValueWithMetadata;

public class UserPropertyEditor extends AbstractPropertyEditor<User> {
    interface Binder extends UiBinder<Widget, UserPropertyEditor> {
    }

    @UiField
    TextBox email;
    @UiField
    TextBox authDomain;
    @UiField
    TextBox userId;
    @UiField
    TextBox federatedIdentity;

    private final JSONValue property;

    @Inject
    UserPropertyEditor(Binder uiBinder,
                       @Assisted String key,
                       @Assisted JSONValue property) {
        super(key);

        this.property = property;

        initFormWidget(uiBinder.createAndBindUi(this));
        setInitialValue();
    }

    @Override
    public JSONValue getJsonValue() {
        JSONObject user = getValue().asJsonObject();

        return parseJsonValueWithMetadata(user, PropertyType.USER, PropertyUtil.isPropertyIndexed(property));
    }

    @Override
    public void setValue(User user) {
        email.setValue(user.getEmail());
        authDomain.setValue(user.getAuthDomain());
        userId.setValue(user.getUserId());
        federatedIdentity.setValue(user.getFederatedIdentity());
    }

    @Override
    public User getValue() {
        User user = new User();
        user.setEmail(email.getValue());
        user.setAuthDomain(authDomain.getValue());
        user.setUserId(userId.getValue());
        user.setFederatedIdentity(federatedIdentity.getValue());

        return user;
    }

    private void setInitialValue() {
        JSONObject userObject = PropertyUtil.getPropertyValue(property).isObject();

        setValue(User.fromJsonObject(userObject));
    }
}
