/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.common.base.Strings;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.LongBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.parseJsonValueWithMetadata;

public class KeyPropertyEditor extends AbstractPropertyEditor<Key> {
    interface Binder extends UiBinder<Widget, KeyPropertyEditor> {
    }

    @UiField
    LongBox id;
    @UiField
    TextBox appId;
    @UiField
    TextBox appIdNamespace;
    @UiField
    TextBox namespace;
    @UiField
    TextBox kind;
    @UiField
    TextBox name;
    @UiField(provided = true)
    RawPropertyEditor parentKey;

    private final AppConstants appConstants;
    private final JSONValue property;

    @Inject
    KeyPropertyEditor(Binder uiBinder,
                      AppConstants appConstants,
                      PropertyEditorFactory propertyEditorFactory,
                      @Assisted String key,
                      @Assisted JSONValue property) {
        super(key);

        this.appConstants = appConstants;
        this.property = property;
        parentKey = (RawPropertyEditor) propertyEditorFactory
                .createRawEditor(PropertyName.PARENT_KEY, property.isObject().get(PropertyName.PARENT_KEY));

        initFormWidget(uiBinder.createAndBindUi(this));
        setInitialValue();
    }

    @Override
    public JSONValue getJsonValue() {
        JSONObject object = getValue().asJsonObject();

        return parseJsonValueWithMetadata(object, PropertyType.KEY, PropertyUtil.isPropertyIndexed(property));
    }

    @Override
    public void setValue(Key key) {
        kind.setText(key.getKind());
        name.setText(key.getName());
        appId.setText(key.getAppId());
        id.setValue(key.getId());

        AppIdNamespaceDto appIdNamespaceDto = key.getAppIdNamespace();
        appIdNamespace.setText(appIdNamespaceDto.getAppId());
        namespace.setText(appIdNamespaceDto.getNamespace());
    }

    @Override
    public Key getValue() {
        Key parentKey = null;
        JSONValue parentKeyObject = this.parentKey.getJsonValue();
        if (parentKeyObject != null && parentKeyObject.isObject() != null) {
            parentKey = Key.fromJsonObject(parentKeyObject.isObject());
        }

        return new Key(kind.getText(), Strings.emptyToNull(name.getText()), Strings.emptyToNull(appId.getText()),
                id.getValue(), new AppIdNamespaceDto(appIdNamespace.getText(), namespace.getText()), parentKey);
    }

    @Override
    protected void showErrors() {
        showError(appConstants.invalidProtocolOrHost());
    }

    private void setInitialValue() {
        JSONObject keyObject = PropertyUtil.getPropertyValue(property).isObject();

        setValue(Key.fromJsonObject(keyObject));
    }
}
