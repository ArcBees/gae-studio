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

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.shared.PropertyName.VALUE;

public class CollectionPropertyEditor extends AbstractPropertyEditor<Collection<?>> {
    PropertyEditorCollectionWidget widget;

    private JSONValue property;

    @Inject
    CollectionPropertyEditor(
            PropertyEditorCollectionWidgetFactory factory,
            AppResources appResources,
            @Assisted String key,
            @Assisted JSONValue property) {
        super(key);

        this.property = property;

        JSONArray array = extractArray(property);
        JSONObject object = new JSONObject();

        if (array != null) {
            for (int i = 0; i < array.size(); i++) {
                object.put(Integer.toString(i), array.get(i));
            }
        }

        widget = factory.create(object);
        widget.asWidget().addStyleName(appResources.styles().embeddedEntityProperties());
        widget.asWidget().addStyleName(appResources.styles().collectionEditor());

        IsWidget form = widget.asWidget();
        initFormWidget(form);
    }

    @Override
    protected boolean validate() {
        Map<String, ?> propertyEditors = widget.getValue();

        boolean isEachChildValid = true;
        for (String property : propertyEditors.keySet()) {
            PropertyEditor<?> editor = (PropertyEditor<?>) propertyEditors.get(property);
            boolean editorValid = editor.isValid();
            isEachChildValid &= editorValid;
        }

        return isEachChildValid;
    }

    @Override
    public JSONValue getJsonValue() {
        Map<String, ?> propertyEditors = widget.getValue();

        JSONObject copy = JSONParser.parseStrict(property.toString()).isObject();
        JSONArray array = extractArray(copy);

        for (String property : propertyEditors.keySet()) {
            PropertyEditor<?> editor = (PropertyEditor<?>) propertyEditors.get(property);
            JSONValue jsonValue = editor.getJsonValue();
            array.set(Integer.valueOf(property), jsonValue);
        }

        return copy;
    }

    private JSONArray extractArray(JSONValue property) {
        JSONObject jsonObject = property.isObject();
        JSONValue arrayValue = jsonObject.get(VALUE);

        return arrayValue.isArray();
    }
}
