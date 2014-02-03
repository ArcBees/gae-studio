/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppConstants;
import com.google.common.base.Strings;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.assistedinject.Assisted;

public class RawPropertyEditor extends AbstractPropertyEditor<String> {
    private final TextBox textBox;
    private final AppConstants appConstants;

    @Inject
    RawPropertyEditor(AppConstants appConstants,
                      @Assisted String key,
                      @Assisted JSONValue property) {
        super(key);

        this.appConstants = appConstants;
        textBox = new TextBox();

        initFormWidget(textBox);
        setValue(property == null ? "" : property.toString());
    }

    @Override
    public JSONValue getJsonValue() {
        String value = getValue();

        if (Strings.isNullOrEmpty(value)) {
            return JSONNull.getInstance();
        } else {
            return JSONParser.parseStrict(value);
        }
    }

    @Override
    protected boolean validate() {
        return jsonParsingDoesNotThrow();
    }

    @Override
    protected void showErrors() {
        showError(appConstants.invalidJson());
    }

    private String getValue() {
        return textBox.getValue();
    }

    private void setValue(String value) {
        textBox.setValue(value);
    }

    private boolean jsonParsingDoesNotThrow() {
        try {
            getJsonValue();
            return true;
        } catch (JSONException e) {
            return false;
        }
    }
}
