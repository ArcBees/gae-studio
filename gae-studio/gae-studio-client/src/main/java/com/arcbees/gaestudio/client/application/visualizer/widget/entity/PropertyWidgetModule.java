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

import java.util.Date;

import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityEditorPresenter.MyView;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class PropertyWidgetModule extends AbstractPresenterModule {
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

        bindSharedView(MyView.class, EntityEditorView.class);
    }
}
