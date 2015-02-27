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

import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.shared.PropertyType;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.safehtml.shared.UriUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.parseJsonValueWithMetadata;

public class IMHandlePropertyEditor extends AbstractPropertyEditor<IMHandle> {
    interface Binder extends UiBinder<Widget, IMHandlePropertyEditor> {
    }

    private static final Set<String> SCHEMES = Sets.newHashSet("sip", "unknown", "xmpp");

    @UiField
    TextBox protocol;
    @UiField
    TextBox address;

    private final AppConstants appConstants;
    private final JSONValue property;

    @Inject
    IMHandlePropertyEditor(
            Binder uiBinder,
            AppConstants appConstants,
            @Assisted String key,
            @Assisted JSONValue property) {
        super(key);

        this.appConstants = appConstants;
        this.property = property;

        initFormWidget(uiBinder.createAndBindUi(this));
        setInitialValue();
    }

    @Override
    public JSONValue getJsonValue() {
        JSONObject object = getValue().asJsonObject();

        return parseJsonValueWithMetadata(object, PropertyType.IM_HANDLE, PropertyUtil.isPropertyIndexed(property));
    }

    @Override
    public boolean validate() {
        boolean valid;

        if (isValidScheme()) {
            valid = hasAddress();
        } else {
            valid = isValidUrl();
        }

        return valid;
    }

    @Override
    protected void showErrors() {
        showError(appConstants.invalidProtocolOrHost());
    }

    private IMHandle getValue() {
        return new IMHandle(protocol.getValue(), address.getValue());
    }

    private void setValue(IMHandle imHandle) {
        protocol.setValue(imHandle.getProtocol());
        address.setValue(imHandle.getAddress());
    }

    private boolean isValidUrl() {
        return !Strings.isNullOrEmpty(protocol.getText()) && UriUtils.isSafeUri(protocol.getText());
    }

    private void setInitialValue() {
        JSONObject imHandleObject = PropertyUtil.getPropertyValue(property).isObject();

        setValue(IMHandle.fromJsonObject(imHandleObject));
    }

    private boolean isValidScheme() {
        return SCHEMES.contains(protocol.getText().toLowerCase());
    }

    private boolean hasAddress() {
        return !Strings.isNullOrEmpty(address.getText());
    }
}
