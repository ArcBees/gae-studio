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
import java.util.Map;

import javax.inject.Singleton;

import com.arcbees.gaestudio.client.application.visualizer.widget.entity.EntityEditorPresenter.MyView;
import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import static com.arcbees.gaestudio.shared.PropertyType.BLOB_KEY;
import static com.arcbees.gaestudio.shared.PropertyType.CATEGORY;
import static com.arcbees.gaestudio.shared.PropertyType.EMAIL;
import static com.arcbees.gaestudio.shared.PropertyType.LINK;
import static com.arcbees.gaestudio.shared.PropertyType.PHONE_NUMBER;
import static com.arcbees.gaestudio.shared.PropertyType.POSTAL_ADDRESS;
import static com.google.inject.name.Names.named;

public class EntityWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder()
                .implement(new TypeLiteral<PropertyEditor<String>>() {}, StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {
                }, named(POSTAL_ADDRESS.name()),
                        StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {}, named(CATEGORY.name()),
                        StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {}, named(EMAIL.name()),
                        StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {}, named(PHONE_NUMBER.name()),
                        StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {}, named(BLOB_KEY.name()),
                        StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {
                }, named(LINK.name()), LinkPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {}, named(LINK.name()), LinkPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<BlobInfoDto>>() {}, BlobKeyPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Long>>() {}, LongPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Long>>() {}, named("RATING"), LongPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Double>>() {}, DoublePropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Boolean>>() {}, BooleanPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Date>>() {}, DatePropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<GeoPoint>>() {}, GeoPointPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<IMHandle>>() {}, IMHandlePropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<User>>() {}, UserPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Map<String, ?>>>() {}, EmbeddedEntityPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<?>>() {}, RawPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Key>>() {}, KeyPropertyEditor.class)
                .build(PropertyEditorFactory.class));

        bind(GeoPointPropertyEditor.Binder.class).in(Singleton.class);

        install(new GinFactoryModuleBuilder().build(EntityEditorFactory.class));

        bind(PropertyEditorCollectionWidgetFactory.class).to(PropertyEditorCollectionWidgetFactoryImpl.class)
                .in(Singleton.class);
        bindSharedView(MyView.class, EntityEditorView.class);

        requestStaticInjection(AbstractPropertyEditor.class);
    }
}
