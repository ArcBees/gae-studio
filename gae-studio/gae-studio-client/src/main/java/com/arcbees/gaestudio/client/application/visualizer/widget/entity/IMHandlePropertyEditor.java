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

public class IMHandlePropertyEditor extends AbstractPropertyEditor<IMHandle> {
    interface Binder extends UiBinder<Widget, IMHandlePropertyEditor> {
    }

    @UiField
    TextBox protocol;
    @UiField
    TextBox address;

    private final JSONValue property;

    @Inject
    IMHandlePropertyEditor(Binder uiBinder,
                           @Assisted String key,
                           @Assisted JSONValue property) {
        super(key);

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
    public void setValue(IMHandle imHandle) {
        protocol.setValue(imHandle.getProtocol());
        address.setValue(imHandle.getAddress());
    }

    @Override
    public IMHandle getValue() {
        return new IMHandle(protocol.getValue(), address.getValue());
    }

    private void setInitialValue() {
        JSONObject imHandleObject = PropertyUtil.getPropertyValue(property).isObject();

        setValue(IMHandle.fromJsonObject(imHandleObject));
    }
}
