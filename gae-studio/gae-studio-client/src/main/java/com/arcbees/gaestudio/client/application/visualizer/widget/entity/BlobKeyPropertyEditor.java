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

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.widget.entity.FetchBlobKeysRunner.FetchBlobKeysCallback;
import com.arcbees.gaestudio.client.ui.BlobInfoRenderer;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.BlobInfoDto;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.parseJsonValueWithMetadata;

public class BlobKeyPropertyEditor extends AbstractPropertyEditor<BlobInfoDto> implements FetchBlobKeysCallback {
    private final ValueListBox<BlobInfoDto> listBox;
    private final JSONValue property;

    @Inject
    BlobKeyPropertyEditor(BlobInfoRenderer blobInfoRenderer,
                          @Assisted String key,
                          @Assisted JSONValue property,
                          @Assisted FetchBlobKeysRunner fetchBlobKeysRunner) {
        super(key);

        this.property = property;
        listBox = new ValueListBox<BlobInfoDto>(blobInfoRenderer);

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

    @Override
    public void setValue(BlobInfoDto value) {
        listBox.setValue(value);
    }

    @Override
    public BlobInfoDto getValue() {
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
