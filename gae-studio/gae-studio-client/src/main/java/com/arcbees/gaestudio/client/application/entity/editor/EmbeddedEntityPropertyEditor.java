/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import java.util.Map;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.shared.PropertyName.PROPERTY_MAP;

public class EmbeddedEntityPropertyEditor extends AbstractPropertyEditor<Map<String, ?>> {
    private final PropertyEditorCollectionWidget propertyEditorsWidget;
    private final JSONValue property;

    @Inject
    EmbeddedEntityPropertyEditor(
            AppResources resources,
            PropertyEditorCollectionWidgetFactory propertyEditorCollectionWidgetFactory,
            @Assisted String key,
            @Assisted JSONValue property) {
        super(key);

        this.property = property;

        JSONObject propertyMap = PropertyUtil.getPropertyValue(property).isObject().get(PROPERTY_MAP).isObject();
        propertyEditorsWidget = propertyEditorCollectionWidgetFactory.create(propertyMap);

        propertyEditorsWidget.asWidget().addStyleName(resources.styles().embeddedEntityProperties());

        initFormWidget(propertyEditorsWidget);
    }

    @Override
    public JSONValue getJsonValue() {
        propertyEditorsWidget.flush();

        return property;
    }
}
