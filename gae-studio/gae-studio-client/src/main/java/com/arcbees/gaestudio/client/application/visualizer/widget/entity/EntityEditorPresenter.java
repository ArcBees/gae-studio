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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityEditorPresenter.MyView;
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
            default:
                propertyEditor = propertyEditorFactory.createRawEditor(key, property);
        }

        propertyEditors.put(key, propertyEditor);

        getView().addPropertyEditor(propertyEditor);
    }
}
