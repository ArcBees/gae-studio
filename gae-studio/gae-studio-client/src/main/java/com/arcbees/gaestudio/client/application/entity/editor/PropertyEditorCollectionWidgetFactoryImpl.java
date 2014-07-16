/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.rest.BlobsService;
import com.arcbees.gaestudio.client.rest.KindsService;
import com.arcbees.gaestudio.client.rest.NamespacesService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.google.common.collect.Maps;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;

public class PropertyEditorCollectionWidgetFactoryImpl implements PropertyEditorCollectionWidgetFactory {
    private final FetchBlobKeysRunner fetchBlobKeysRunner = new FetchBlobKeysRunner() {
        @Override
        public void fetch(final FetchBlobKeysCallback callback) {
            getRestDispatch().execute(blobsService.getAllKeys(), new AsyncCallbackImpl<List<BlobInfoDto>>() {
                @Override
                public void onSuccess(List<BlobInfoDto> blobInfoDtos) {
                    callback.onBlobKeysFetched(blobInfoDtos);
                }
            });
        }
    };
    private final FetchKindsRunner fetchKindsRunner = new FetchKindsRunner() {
        @Override
        public void fetch(final FetchKindsCallback callback) {
            getRestDispatch().execute(kindsService.getKinds(), new AsyncCallbackImpl<List<String>>() {
                @Override
                public void onSuccess(List<String> kinds) {
                    callback.onKindsFetched(kinds);
                }
            });
        }
    };
    private final FetchNamespacesRunner fetchNamespacesRunner = new FetchNamespacesRunner() {
        @Override
        public void fetch(final FetchNamespacesCallback callback) {
            getRestDispatch().execute(namespacesService.getNamespaces(),
                    new AsyncCallbackImpl<List<AppIdNamespaceDto>>() {
                        @Override
                        public void onSuccess(List<AppIdNamespaceDto> namespaces) {
                            callback.onNamespacesFetched(namespaces);
                        }
                    });
        }
    };

    private final AppResources resources;
    private final PropertyEditorFactory propertyEditorFactory;
    private final RestDispatch restDispatch;
    private final BlobsService blobsService;
    private final KindsService kindsService;
    private final NamespacesService namespacesService;

    @Inject
    PropertyEditorCollectionWidgetFactoryImpl(AppResources resources,
                                              PropertyEditorFactory propertyEditorFactory,
                                              RestDispatch restDispatch,
                                              BlobsService blobsService,
                                              KindsService kindsService,
                                              NamespacesService namespacesService) {
        this.resources = resources;
        this.propertyEditorFactory = propertyEditorFactory;
        this.restDispatch = restDispatch;
        this.blobsService = blobsService;
        this.kindsService = kindsService;
        this.namespacesService = namespacesService;
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
            propertyEditor =
                    propertyEditorFactory.createKeyEditor(key, property, fetchKindsRunner, fetchNamespacesRunner);
        } else if (propertyType == PropertyType.BLOB || propertyType == PropertyType.SHORT_BLOB) {
            propertyEditor = propertyEditorFactory.createBytesEditor(key, property);
        } else if (propertyType == PropertyType.COLLECTION) {
            propertyEditor = propertyEditorFactory.createCollectionEditor(key, property);
        } else {
            propertyEditor = propertyEditorFactory.createRawEditor(key, property);
        }

        return propertyEditor;
    }

    private RestDispatch getRestDispatch() {
        return restDispatch;
    }
}
