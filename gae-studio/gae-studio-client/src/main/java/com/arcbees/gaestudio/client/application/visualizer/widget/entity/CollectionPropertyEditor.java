/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.shared.PropertyName.VALUE;

public class CollectionPropertyEditor extends AbstractPropertyEditor<Collection<?>> {
    PropertyEditorCollectionWidget widget;

    private JSONValue property;

    @Inject
    CollectionPropertyEditor(PropertyEditorCollectionWidgetFactory factory,
                             AppResources appResources,
                             @Assisted String key,
                             @Assisted JSONValue property) {
        super(key);

        this.property = property;

        JSONArray array = extractArray(property);
        JSONObject object = new JSONObject();

        for (int i = 0; i < array.size(); i++) {
            object.put(Integer.toString(i), array.get(i));
        }

        widget = factory.create(object);
        widget.asWidget().addStyleName(appResources.styles().embeddedEntityProperties());

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

        JSONArray array = extractArray(property);

        for (String property : propertyEditors.keySet()) {
            PropertyEditor<?> editor = (PropertyEditor<?>) propertyEditors.get(property);
            JSONValue jsonValue = editor.getJsonValue();
            array.set(Integer.valueOf(property), jsonValue);
        }

        return property;
    }

    @Override
    public Collection<?> getValue() {
        return null; //these method never seem to be invoked
    }

    @Override
    public void setValue(Collection<?> value) {
        //these method never seem to be invoked
    }

    private JSONArray extractArray(JSONValue property) {
        JSONObject jsonObject = property.isObject();
        JSONValue arrayValue = jsonObject.get(VALUE);

        return arrayValue.isArray();
    }
}
