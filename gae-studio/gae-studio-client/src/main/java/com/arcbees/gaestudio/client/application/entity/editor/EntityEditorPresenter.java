/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.entity.editor.EntityEditorPresenter.MyView;
import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.arcbees.gaestudio.client.util.KeyPrettifier.KeyPrettifier;
import com.arcbees.gaestudio.shared.PropertyName;
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

    @Inject
    EntityEditorPresenter(EventBus eventBus,
                          MyView view,
                          KeyPrettifier keyPrettifier,
                          PropertyEditorCollectionWidgetFactory propertyEditorCollectionWidgetFactory,
                          @Assisted ParsedEntity entity) {
        super(eventBus, view);

        this.entity = entity;
        this.keyPrettifier = keyPrettifier;
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
        String text = keyPrettifier.prettifyKey(key);

        getView().setHeader(text);
        getView().addPropertyEditor(propertyEditorsWidget);
    }
}
