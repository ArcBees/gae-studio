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

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.assistedinject.Assisted;

public class RawPropertyEditor extends AbstractPropertyEditor<String> {
    private final TextBox textBox;

    @Inject
    RawPropertyEditor(@Assisted String key,
                      @Assisted JSONValue property) {
        super(key);

        textBox = new TextBox();

        initFormWidget(textBox);
        setValue(property.toString());
    }

    @Override
    public JSONValue getJsonValue() {
        return JSONParser.parseStrict(getValue());
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
