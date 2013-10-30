/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.rest.BlobsService;
import com.arcbees.gaestudio.client.util.MethodCallbackImpl;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.google.common.collect.Maps;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class PropertyEditorCollectionWidgetFactoryImpl implements PropertyEditorCollectionWidgetFactory {
    private final FetchBlobKeysRunner fetchBlobKeysRunner = new FetchBlobKeysRunner() {
        @Override
        public void fetch(final FetchBlobKeysCallback callback) {
            blobsService.getAllKeys(new MethodCallbackImpl<List<BlobInfoDto>>() {
                @Override
                protected void onSuccess(List<BlobInfoDto> blobInfoDtos) {
                    callback.onBlobKeysFetched(blobInfoDtos);
                }
            });
        }
    };

    private final PropertyEditorFactory propertyEditorFactory;
    private final BlobsService blobsService;

    @Inject
    PropertyEditorCollectionWidgetFactoryImpl(PropertyEditorFactory propertyEditorFactory,
                                              BlobsService blobsService) {
        this.propertyEditorFactory = propertyEditorFactory;
        this.blobsService = blobsService;
    }

    @Override
    public PropertyEditorCollectionWidget create(JSONObject propertyMap) {
        Map<String, PropertyEditor<?>> propertyEditors = Maps.newTreeMap();

        for (String key : propertyMap.keySet()) {
            JSONValue property = propertyMap.get(key);
            PropertyType propertyType = PropertyUtil.getPropertyType(property);
            PropertyEditor<?> propertyEditor = createPropertyEditor(key, propertyType, property);

            propertyEditors.put(key, propertyEditor);
        }

        return new PropertyEditorCollectionWidget(propertyEditors, propertyMap);
    }

    private PropertyEditor<?> createPropertyEditor(String key, PropertyType propertyType, JSONValue property) {
        PropertyEditor<?> propertyEditor;

        if (propertyType == PropertyType.STRING) {
            propertyEditor = propertyEditorFactory.createStringEditor(key, property);
        } else if (propertyType == PropertyType.NUMERIC) {
            propertyEditor = propertyEditorFactory.createNumericEditor(key, property);
        } else if (propertyType == PropertyType.FLOATING) {
            propertyEditor = propertyEditorFactory.createFloatingEditor(key, property);
        } else if (propertyType == PropertyType.DATE) {
            propertyEditor = propertyEditorFactory.createDateEditor(key, property);
        } else if (propertyType == PropertyType.BOOLEAN) {
            propertyEditor = propertyEditorFactory.createBooleanEditor(key, property);
        } else if (propertyType == PropertyType.POSTAL_ADDRESS) {
            propertyEditor = propertyEditorFactory.createPostalAddressEditor(key, property);
        } else if (propertyType == PropertyType.CATEGORY) {
            propertyEditor = propertyEditorFactory.createCategoryEditor(key, property);
        } else if (propertyType == PropertyType.LINK) {
            propertyEditor = propertyEditorFactory.createLinkEditor(key, property);
        } else if (propertyType == PropertyType.EMAIL) {
            propertyEditor = propertyEditorFactory.createEmailEditor(key, property);
        } else if (propertyType == PropertyType.PHONE_NUMBER) {
            propertyEditor = propertyEditorFactory.createPhoneNumberEditor(key, property);
        } else if (propertyType == PropertyType.BLOB_KEY) {
            return propertyEditorFactory.createBlobKeyEditor(key, property, fetchBlobKeysRunner);
        } else if (propertyType == PropertyType.RATING) {
            propertyEditor = propertyEditorFactory.createRatingEditor(key, property);
        } else if (propertyType == PropertyType.GEO_PT) {
            propertyEditor = propertyEditorFactory.createGeoPointEditor(key, property);
        } else if (propertyType == PropertyType.IM_HANDLE) {
            propertyEditor = propertyEditorFactory.createIMHandleEditor(key, property);
        } else if (propertyType == PropertyType.USER) {
            propertyEditor = propertyEditorFactory.createUserEditor(key, property);
        } else if (propertyType == PropertyType.EMBEDDED) {
            propertyEditor = propertyEditorFactory.createEmbeddedEntityEditor(key, property);
        } else if (propertyType == PropertyType.KEY) {
            propertyEditor = propertyEditorFactory.createKeyEditor(key, property);
        } else {
            propertyEditor = propertyEditorFactory.createRawEditor(key, property);
        }

        return propertyEditor;
    }
}
