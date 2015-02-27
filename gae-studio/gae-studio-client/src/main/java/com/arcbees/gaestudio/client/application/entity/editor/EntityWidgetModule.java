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

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.inject.Singleton;

import com.arcbees.gaestudio.client.application.entity.editor.EntityEditorPresenter.MyView;
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
                .implement(new TypeLiteral<PropertyEditor<String>>() {
                }, StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {
                }, named(POSTAL_ADDRESS.name()), StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {
                }, named(CATEGORY.name()), StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {
                }, named(EMAIL.name()), StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {
                }, named(PHONE_NUMBER.name()), StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {
                }, named(BLOB_KEY.name()), StringPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {
                }, named("BYTES"), BytesPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<String>>() {
                }, named(LINK.name()), LinkPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<BlobInfoDto>>() {
                }, BlobKeyPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Long>>() {
                }, LongPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Long>>() {
                }, named("RATING"), RatingPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Double>>() {
                }, DoublePropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Boolean>>() {
                }, BooleanPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Date>>() {
                }, DatePropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<GeoPoint>>() {
                }, GeoPointPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<IMHandle>>() {
                }, IMHandlePropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<User>>() {
                }, UserPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Map<String, ?>>>() {
                }, EmbeddedEntityPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<?>>() {
                }, RawPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Key>>() {
                }, KeyPropertyEditor.class)
                .implement(new TypeLiteral<PropertyEditor<Collection<?>>>() {
                }, CollectionPropertyEditor.class)
                .build(PropertyEditorsFactory.class));

        bind(GeoPointPropertyEditor.Binder.class).in(Singleton.class);

        install(new GinFactoryModuleBuilder().build(EntityEditorFactory.class));

        bind(PropertyEditorCollectionWidgetFactory.class)
                .to(PropertyEditorCollectionWidgetFactoryImpl.class)
                .in(Singleton.class);
        bind(PropertyEditorFactory.class)
                .to(PropertyEditorFactoryImpl.class)
                .in(Singleton.class);

        bindSharedView(MyView.class, EntityEditorView.class);
    }
}
