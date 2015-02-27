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

package com.arcbees.gaestudio.client.application.entity.editor;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.parseJsonValueWithMetadata;

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
    UserPropertyEditor(
            Binder uiBinder,
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

    private void setValue(User user) {
        email.setValue(user.getEmail());
        authDomain.setValue(user.getAuthDomain());
        userId.setValue(user.getUserId());
        federatedIdentity.setValue(user.getFederatedIdentity());
    }

    private User getValue() {
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
