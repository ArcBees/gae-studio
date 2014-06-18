package com.arcbees.gaestudio.client.application.entity.editor;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.rest.BlobsService;
import com.arcbees.gaestudio.client.rest.KindsService;
import com.arcbees.gaestudio.client.rest.NamespacesService;
import com.arcbees.gaestudio.client.util.AsyncCallbackImpl;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.google.gwt.json.client.JSONValue;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;

public class PropertyEditorFactoryImpl implements PropertyEditorFactory {
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

    @Inject
    PropertyEditorFactoryImpl(PropertyEditorsFactory propertyEditorsFactory,
                              RestDispatch restDispatch,
                              BlobsService blobsService,
                              KindsService kindsService,
                              NamespacesService namespacesService) {
        this.propertyEditorsFactory = propertyEditorsFactory;
        this.restDispatch = restDispatch;
        this.blobsService = blobsService;
        this.kindsService = kindsService;
        this.namespacesService = namespacesService;
    }

    @Override
    public PropertyEditor create(String key,
                                 PropertyType propertyType,
                                 JSONValue property) {
        PropertyEditor<?> propertyEditor;

        if (propertyType == PropertyType.STRING) {
            propertyEditor = propertyEditorsFactory.createStringEditor(key, property);
        } else if (propertyType == PropertyType.NUMERIC) {
            propertyEditor = propertyEditorsFactory.createNumericEditor(key, property);
        } else if (propertyType == PropertyType.FLOATING) {
            propertyEditor = propertyEditorsFactory.createFloatingEditor(key, property);
        } else if (propertyType == PropertyType.DATE) {
            propertyEditor = propertyEditorsFactory.createDateEditor(key, property);
        } else if (propertyType == PropertyType.BOOLEAN) {
            propertyEditor = propertyEditorsFactory.createBooleanEditor(key, property);
        } else if (propertyType == PropertyType.POSTAL_ADDRESS) {
            propertyEditor = propertyEditorsFactory.createPostalAddressEditor(key, property);
        } else if (propertyType == PropertyType.CATEGORY) {
            propertyEditor = propertyEditorsFactory.createCategoryEditor(key, property);
        } else if (propertyType == PropertyType.LINK) {
            propertyEditor = propertyEditorsFactory.createLinkEditor(key, property);
        } else if (propertyType == PropertyType.EMAIL) {
            propertyEditor = propertyEditorsFactory.createEmailEditor(key, property);
        } else if (propertyType == PropertyType.PHONE_NUMBER) {
            propertyEditor = propertyEditorsFactory.createPhoneNumberEditor(key, property);
        } else if (propertyType == PropertyType.BLOB_KEY) {
            return propertyEditorsFactory.createBlobKeyEditor(key, property, fetchBlobKeysRunner);
        } else if (propertyType == PropertyType.RATING) {
            propertyEditor = propertyEditorsFactory.createRatingEditor(key, property);
        } else if (propertyType == PropertyType.GEO_PT) {
            propertyEditor = propertyEditorsFactory.createGeoPointEditor(key, property);
        } else if (propertyType == PropertyType.IM_HANDLE) {
            propertyEditor = propertyEditorsFactory.createIMHandleEditor(key, property);
        } else if (propertyType == PropertyType.USER) {
            propertyEditor = propertyEditorsFactory.createUserEditor(key, property);
        } else if (propertyType == PropertyType.EMBEDDED) {
            propertyEditor = propertyEditorsFactory.createEmbeddedEntityEditor(key, property);
        } else if (propertyType == PropertyType.KEY) {
            propertyEditor =
                    propertyEditorsFactory.createKeyEditor(key, property, fetchKindsRunner, fetchNamespacesRunner);
        } else if (propertyType == PropertyType.BLOB || propertyType == PropertyType.SHORT_BLOB) {
            propertyEditor = propertyEditorsFactory.createBytesEditor(key, property);
        } else if (propertyType == PropertyType.COLLECTION) {
            propertyEditor = propertyEditorsFactory.createCollectionEditor(key, property);
        } else {
            propertyEditor = propertyEditorsFactory.createRawEditor(key, property);
        }

        return propertyEditor;
    }

    private RestDispatch getRestDispatch() {
        return restDispatch;
    }
}
