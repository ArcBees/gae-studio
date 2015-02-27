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

import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.shared.PropertyType;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.parseJsonValueWithMetadata;

public class BooleanPropertyEditor extends AbstractPropertyEditorWithWidgetLeftside<Boolean> {
    private final CustomCheckBox checkBox;
    private final JSONValue property;

    @Inject
    BooleanPropertyEditor(
            CustomCheckBox customCheckBox,
            AppResources appResources,
            @Assisted String key,
            @Assisted JSONValue property) {
        super(key);

        this.property = property;
        this.checkBox = customCheckBox;

        initFormWidget(checkBox);
        setValue(PropertyUtil.getPropertyValue(property).isBoolean().booleanValue());

        setKeyStyle(appResources.styles().booleanKey());
    }

    @Override
    public JSONValue getJsonValue() {
        JSONValue value = JSONBoolean.getInstance(getValue());
        return parseJsonValueWithMetadata(value, PropertyType.BOOLEAN, PropertyUtil.isPropertyIndexed(property));
    }

    private void setValue(Boolean value) {
        checkBox.setValue(value);
    }

    private Boolean getValue() {
        return checkBox.getValue();
    }
}
