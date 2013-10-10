/**
 * Copyright 2011 ArcBees Inc.
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

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.parseJsonValueWithMetadata;

public class StringPropertyEditor extends AbstractPropertyEditor<String> {
    private final TextBox textBox;
    private final JSONValue property;

    @Inject
    StringPropertyEditor(@Assisted String key,
                         @Assisted JSONValue property) {
        super(key);

        this.property = property;
        textBox = new TextBox();

        initFormWidget(textBox);
        setValue(PropertyUtil.getPropertyValue(property).isString().stringValue());
    }

    @Override
    public JSONValue getJsonValue() {
        JSONString value = new JSONString(getValue());
        return parseJsonValueWithMetadata(value, PropertyType.STRING, PropertyUtil.isPropertyIndexed(property));
    }

    @Override
    public void setValue(String value) {
        textBox.setValue(value);
    }

    @Override
    public String getValue() {
        return textBox.getValue();
    }
}
