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

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityEditorPresenter.MyView;
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

    private final ParsedEntity entity;

    private final Map<String, PropertyEditor<?>> propertyEditors;

    @Inject
    EntityEditorPresenter(EventBus eventBus,
                          MyView view,
                          PropertyEditorsFactory propertyEditorsFactory,
                          @Assisted ParsedEntity entity) {
        super(eventBus, view);

        this.entity = entity;
        propertyEditors = propertyEditorsFactory.create(entity);

        addPropertyEditorsToView();
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

    private void addPropertyEditorsToView() {
        for (PropertyEditor<?> propertyEditor : propertyEditors.values()) {
            getView().addPropertyEditor(propertyEditor);
        }
    }
}
