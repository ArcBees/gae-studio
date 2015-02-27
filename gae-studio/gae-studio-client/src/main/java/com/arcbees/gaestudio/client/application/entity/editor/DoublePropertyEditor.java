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
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.parseJsonValueWithMetadata;

public class DoublePropertyEditor extends AbstractPropertyEditor<Double> {
    private final DoubleBox doubleBox;
    private final JSONValue property;

    @Inject
    DoublePropertyEditor(
            @Assisted String key,
            @Assisted JSONValue property) {
        super(key);

        this.property = property;
        doubleBox = new DoubleBox();

        initFormWidget(doubleBox);
        setValue(PropertyUtil.getPropertyValue(property).isNumber().doubleValue());
    }

    @Override
    public JSONValue getJsonValue() {
        JSONValue value = new JSONNumber(getValue());
        return parseJsonValueWithMetadata(value, PropertyType.FLOATING, PropertyUtil.isPropertyIndexed(property));
    }

    private void setValue(Double value) {
        doubleBox.setValue(value);
    }

    private Double getValue() {
        return doubleBox.getValue();
    }
}
