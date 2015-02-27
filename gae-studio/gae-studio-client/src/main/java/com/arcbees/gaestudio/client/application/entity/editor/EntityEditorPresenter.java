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

import com.arcbees.gaestudio.client.application.entity.editor.EntityEditorPresenter.MyView;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.util.keyprettifier.KeyDtoMapper;
import com.arcbees.gaestudio.client.util.keyprettifier.KeyPrettifier;
import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class EntityEditorPresenter extends PresenterWidget<MyView> {
    public interface MyView extends View {
        void addPropertyEditor(IsWidget widget);

        void setHeader(String text);
    }

    private final ParsedEntity entity;
    private final PropertyEditorCollectionWidget propertyEditorsWidget;
    private final KeyPrettifier keyPrettifier;
    private final KeyDtoMapper keyDtoMapper;

    @Inject
    EntityEditorPresenter(
            EventBus eventBus,
            MyView view,
            KeyPrettifier keyPrettifier,
            PropertyEditorCollectionWidgetFactory propertyEditorCollectionWidgetFactory,
            KeyDtoMapper keyDtoMapper,
            @Assisted ParsedEntity entity) {
        super(eventBus, view);

        this.entity = entity;
        this.keyPrettifier = keyPrettifier;
        this.keyDtoMapper = keyDtoMapper;
        propertyEditorsWidget = propertyEditorCollectionWidgetFactory.create(entity.getPropertyMap());
    }

    public ParsedEntity flush() throws InvalidEntityFieldsException {
        propertyEditorsWidget.flush();

        entity.getEntityDto().setJson(entity.getJson());

        return entity;
    }

    @Override
    protected void onBind() {
        super.onBind();

        JSONObject key = entity.getJsonObject().get(PropertyName.KEY).isObject();
        KeyDto keyDto = keyDtoMapper.fromJSONObject(key);
        String text = keyPrettifier.prettifyKey(keyDto);

        getView().setHeader(text);
        getView().addPropertyEditor(propertyEditorsWidget);
    }
}
