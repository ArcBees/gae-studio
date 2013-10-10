/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityEditorPresenter.MyView;
import com.arcbees.gaestudio.shared.PropertyType;
import com.google.common.collect.Maps;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class EntityEditorPresenter extends PresenterWidget<MyView> {
    public interface MyView extends View {
        void addPropertyEditor(IsWidget widget);
    }

    private final PropertyEditorFactory propertyEditorFactory;
    private final ParsedEntity entity;

    private final Map<String, PropertyEditor<?>> propertyEditors = Maps.newHashMap();

    @Inject
    EntityEditorPresenter(EventBus eventBus,
                          MyView view,
                          PropertyEditorFactory propertyEditorFactory,
                          @Assisted ParsedEntity entity) {
        super(eventBus, view);

        this.propertyEditorFactory = propertyEditorFactory;
        this.entity = entity;

        createPropertyEditors();
    }

    public ParsedEntity flush() {
        for (Entry<String, PropertyEditor<?>> entry : propertyEditors.entrySet()) {
            String key = entry.getKey();
            PropertyEditor<?> propertyEditor = entry.getValue();

            JSONValue newJsonValue = propertyEditor.getJsonValue();

            JSONObject properties = entity.getPropertyMap();
            properties.put(key, newJsonValue);
        }

        entity.getEntityDto().setJson(entity.getJson());

        return entity;
    }

    private void createPropertyEditors() {
        Set<String> keys = entity.getPropertyMap().keySet();

        for (String key : keys) {
            JSONValue property = entity.getProperty(key);
            createPropertyEditor(key, PropertyUtil.getPropertyType(property), property);
        }
    }

    private void createPropertyEditor(String key, PropertyType propertyType, JSONValue property) {
        PropertyEditor<?> propertyEditor;
        switch (propertyType) {
            case STRING:
                propertyEditor = propertyEditorFactory.createStringEditor(key, property);
                break;
            case NUMERIC:
                propertyEditor = propertyEditorFactory.createNumericEditor(key, property);
                break;
            case FLOATING:
                propertyEditor = propertyEditorFactory.createFloatingEditor(key, property);
                break;
            case DATE:
                propertyEditor = propertyEditorFactory.createDateEditor(key, property);
                break;
            case BOOLEAN:
                propertyEditor = propertyEditorFactory.createBooleanEditor(key, property);
                break;
            case POSTAL_ADDRESS:
                propertyEditor = propertyEditorFactory.createPostalAddressEditor(key, property);
                break;
            case CATEGORY:
                propertyEditor = propertyEditorFactory.createCategoryEditor(key, property);
                break;
            case LINK:
                propertyEditor = propertyEditorFactory.createLinkEditor(key, property);
                break;
            case EMAIL:
                propertyEditor = propertyEditorFactory.createEmailEditor(key, property);
                break;
            case PHONE_NUMBER:
                propertyEditor = propertyEditorFactory.createPhoneNumberEditor(key, property);
                break;
            case RATING:
                propertyEditor = propertyEditorFactory.createRatingEditor(key, property);
                break;
            default:
                propertyEditor = propertyEditorFactory.createRawEditor(key, property);
        }

        propertyEditors.put(key, propertyEditor);

        getView().addPropertyEditor(propertyEditor);
    }
}
