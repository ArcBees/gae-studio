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

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.entity.editor.FetchBlobKeysRunner.FetchBlobKeysCallback;
import com.arcbees.gaestudio.client.ui.BlobInfoRenderer;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.parseJsonValueWithMetadata;

public class BlobKeyPropertyEditor extends AbstractPropertyEditor<BlobInfoDto> implements FetchBlobKeysCallback {
    private final ValueListBox<BlobInfoDto> listBox;
    private final JSONValue property;

    @Inject
    BlobKeyPropertyEditor(
            BlobInfoRenderer blobInfoRenderer,
            @Assisted String key,
            @Assisted JSONValue property,
            @Assisted FetchBlobKeysRunner fetchBlobKeysRunner) {
        super(key);

        this.property = property;
        listBox = new ValueListBox<>(blobInfoRenderer);

        fetchBlobKeys(fetchBlobKeysRunner);

        initFormWidget(listBox);
    }

    @Override
    public void onBlobKeysFetched(List<BlobInfoDto> blobInfos) {
        listBox.setValue(null);
        listBox.setAcceptableValues(blobInfos);

        setValue(PropertyUtil.getPropertyValue(property).isString().stringValue());
    }

    @Override
    public JSONValue getJsonValue() {
        BlobInfoDto blobInfoDto = getValue();
        JSONValue value = blobInfoDto == null ? JSONNull.getInstance() : new JSONString(getValue().getKey());
        Boolean isIndexed = PropertyUtil.isPropertyIndexed(property);
        PropertyType propertyType = PropertyUtil.getPropertyType(property);

        return parseJsonValueWithMetadata(value, propertyType, isIndexed);
    }

    private void setValue(BlobInfoDto value) {
        listBox.setValue(value);
    }

    private BlobInfoDto getValue() {
        return listBox.getValue();
    }

    private void setValue(String blobKey) {
        BlobInfoDto blobInfoDto = new BlobInfoDto();
        blobInfoDto.setKey(blobKey);

        setValue(blobInfoDto);
    }

    private void fetchBlobKeys(FetchBlobKeysRunner fetchBlobKeysRunner) {
        BlobInfoDto loading = new BlobInfoDto();
        loading.setName("Loading keys...");
        setValue(loading);

        fetchBlobKeysRunner.fetch(this);
    }
}
