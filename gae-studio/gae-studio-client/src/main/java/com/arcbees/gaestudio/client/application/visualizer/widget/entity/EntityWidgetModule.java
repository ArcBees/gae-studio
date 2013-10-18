/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import java.util.Date;

import javax.inject.Singleton;

import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityEditorPresenter.MyView;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class EntityWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder()
                .implement(new TypeLiteral<PropertyEditor<String>>() {}, StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Long>>() {}, LongPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Double>>() {}, DoublePropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Boolean>>() {}, BooleanPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Date>>() {}, DatePropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<?>>() {}, RawPropertyEditor.class)
                .build(PropertyEditorFactory.class));

        install(new GinFactoryModuleBuilder().build(EntityEditorFactory.class));

        bind(PropertyEditorsFactory.class).to(PropertyEditorsFactoryImpl.class).in(Singleton.class);
        bindSharedView(MyView.class, EntityEditorView.class);
    }
}
