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
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.LongBox;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.parseJsonValueWithMetadata;

public class LongPropertyEditor extends AbstractPropertyEditor<Long> {
    private final LongBox longBox;
    private final JSONValue property;

    @Inject
    LongPropertyEditor(
            @Assisted String key,
            @Assisted JSONValue property) {
        super(key);

        this.property = property;
        longBox = new LongBox();

        initFormWidget(longBox);

        JSONNumber numberValue = PropertyUtil.getPropertyValue(property).isNumber();
        setValue(numberValue == null ? null : (long) numberValue.doubleValue());
    }

    @Override
    public JSONValue getJsonValue() {
        Long longValue = getValue();
        JSONValue value = longValue == null ? JSONNull.getInstance() : new JSONNumber(longValue);
        Boolean isIndexed = PropertyUtil.isPropertyIndexed(property);
        PropertyType propertyType = PropertyUtil.getPropertyType(property);

        return parseJsonValueWithMetadata(value, propertyType, isIndexed);
    }

    protected Long getValue() {
        return longBox.getValue();
    }

    private void setValue(Long value) {
        longBox.setValue(value);
    }
}
