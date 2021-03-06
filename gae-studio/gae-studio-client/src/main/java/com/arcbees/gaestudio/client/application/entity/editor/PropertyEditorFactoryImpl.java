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

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.rest.BlobsService;
import com.arcbees.gaestudio.client.rest.KindsService;
import com.arcbees.gaestudio.client.rest.NamespacesService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.google.common.collect.Maps;
import com.google.gwt.json.client.JSONValue;
import com.gwtplatform.dispatch.rest.client.RestDispatch;

public class PropertyEditorFactoryImpl implements PropertyEditorFactory {
    private interface EditorFactory {
        PropertyEditor create(String key, JSONValue property);
    }

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
                    }
            );
        }
    };

    private final PropertyEditorsFactory propertyEditorsFactory;
    private final RestDispatch restDispatch;
    private final BlobsService blobsService;
    private final KindsService kindsService;
    private final NamespacesService namespacesService;
    private final Map<PropertyType, EditorFactory> propertyEditorsFactoryMap;

    @Inject
    PropertyEditorFactoryImpl(
            PropertyEditorsFactory propertyEditorsFactory,
            RestDispatch restDispatch,
            BlobsService blobsService,
            KindsService kindsService,
            NamespacesService namespacesService) {
        this.propertyEditorsFactory = propertyEditorsFactory;
        this.restDispatch = restDispatch;
        this.blobsService = blobsService;
        this.kindsService = kindsService;
        this.namespacesService = namespacesService;
        propertyEditorsFactoryMap = Maps.newHashMap();

        createFactoriesMap(propertyEditorsFactory);
    }

    @Override
    public PropertyEditor create(String key,
            PropertyType propertyType,
            JSONValue property) {
        if (propertyEditorsFactoryMap.containsKey(propertyType)) {
            return propertyEditorsFactoryMap.get(propertyType).create(key, property);
        } else {
            return propertyEditorsFactory.createRawEditor(key, property);
        }
    }

    private RestDispatch getRestDispatch() {
        return restDispatch;
    }

    private void createFactoriesMap(final PropertyEditorsFactory propertyEditorsFactory) {
        propertyEditorsFactoryMap.put(PropertyType.STRING, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createStringEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.NUMERIC, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createNumericEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.FLOATING, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createFloatingEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.DATE, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createDateEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.BOOLEAN, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createBooleanEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.POSTAL_ADDRESS, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createPostalAddressEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.CATEGORY, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createCategoryEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.LINK, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createLinkEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.EMAIL, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createEmailEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.PHONE_NUMBER, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createPhoneNumberEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.BLOB_KEY, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createBlobKeyEditor(key, property, fetchBlobKeysRunner);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.RATING, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createRatingEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.GEO_PT, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createGeoPointEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.IM_HANDLE, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createIMHandleEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.USER, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createUserEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.EMBEDDED, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createEmbeddedEntityEditor(key, property);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.KEY, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createKeyEditor(key, property, fetchKindsRunner, fetchNamespacesRunner);
            }
        });
        propertyEditorsFactoryMap.put(PropertyType.COLLECTION, new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createCollectionEditor(key, property);
            }
        });

        EditorFactory bytesEditorFactory = new EditorFactory() {
            @Override
            public PropertyEditor create(String key, JSONValue property) {
                return propertyEditorsFactory.createBytesEditor(key, property);
            }
        };
        propertyEditorsFactoryMap.put(PropertyType.BLOB, bytesEditorFactory);
        propertyEditorsFactoryMap.put(PropertyType.SHORT_BLOB, bytesEditorFactory);
    }
}
