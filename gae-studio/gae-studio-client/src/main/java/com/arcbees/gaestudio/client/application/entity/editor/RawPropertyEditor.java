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
    RawPropertyEditor(
            AppConstants appConstants,
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

    public void setStyleName(String styleName) {
        widget.setStyleName(styleName);
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
