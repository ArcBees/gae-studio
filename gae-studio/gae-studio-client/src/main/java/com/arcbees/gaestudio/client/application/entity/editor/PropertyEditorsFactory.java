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

import javax.inject.Named;

import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.google.gwt.json.client.JSONValue;

public interface PropertyEditorsFactory {
    PropertyEditor<String> createStringEditor(String key, JSONValue property);

    PropertyEditor<Boolean> createBooleanEditor(String key, JSONValue property);

    PropertyEditor<Long> createNumericEditor(String key, JSONValue property);

    PropertyEditor<Double> createFloatingEditor(String key, JSONValue property);

    PropertyEditor<Date> createDateEditor(String key, JSONValue property);

    @Named("POSTAL_ADDRESS")
    PropertyEditor<String> createPostalAddressEditor(String key, JSONValue property);

    @Named("CATEGORY")
    PropertyEditor<String> createCategoryEditor(String key, JSONValue property);

    @Named("LINK")
    PropertyEditor<String> createLinkEditor(String key, JSONValue property);

    @Named("EMAIL")
    PropertyEditor<String> createEmailEditor(String key, JSONValue property);

    @Named("PHONE_NUMBER")
    PropertyEditor<String> createPhoneNumberEditor(String key, JSONValue property);

    PropertyEditor<BlobInfoDto> createBlobKeyEditor(String key, JSONValue property, FetchBlobKeysRunner runner);

    @Named("RATING")
    PropertyEditor<Long> createRatingEditor(String key, JSONValue property);

    PropertyEditor<GeoPoint> createGeoPointEditor(String key, JSONValue property);

    PropertyEditor<IMHandle> createIMHandleEditor(String key, JSONValue property);

    PropertyEditor<User> createUserEditor(String key, JSONValue property);

    PropertyEditor<Map<String, ?>> createEmbeddedEntityEditor(String key, JSONValue property);

    PropertyEditor<?> createRawEditor(String key, JSONValue property);

    PropertyEditor<Key> createKeyEditor(
            String key,
            JSONValue property,
            FetchKindsRunner fetchKindsRunner,
            FetchNamespacesRunner fetchNamespacesRunner);

    @Named("BYTES")
    PropertyEditor<String> createBytesEditor(String key, JSONValue property);

    PropertyEditor<Collection<?>> createCollectionEditor(String key, JSONValue property);
}
