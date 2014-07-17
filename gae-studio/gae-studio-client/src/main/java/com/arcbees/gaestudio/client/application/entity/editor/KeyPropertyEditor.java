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

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.widget.dropdown.Dropdown;
import com.arcbees.gaestudio.client.application.widget.dropdown.DropdownFactory;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.KeyPropertyEditorDropdownResources;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.LongBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.parseJsonValueWithMetadata;

public class KeyPropertyEditor extends AbstractPropertyEditor<Key>
        implements FetchNamespacesRunner.FetchNamespacesCallback, FetchKindsRunner.FetchKindsCallback {

    interface Binder extends UiBinder<Widget, KeyPropertyEditor> {
    }

    private static class StringRenderer extends AbstractRenderer<String> {
        @Override
        public String render(String value) {
            return value;
        }
    }

    @UiField
    LongBox id;
    @UiField
    TextBox appId;
    @UiField(provided = true)
    Dropdown<AppIdNamespaceDto> appIdNamespace;
    @UiField(provided = true)
    Dropdown<AppIdNamespaceDto> namespace;
    @UiField(provided = true)
    Dropdown<String> kind;
    @UiField
    TextBox name;

    private final AppConstants appConstants;
    private final JSONValue property;
    private final NameSpaceValueSetter nameSpaceValueSetter;

    private Key key;

    @Inject
    KeyPropertyEditor(Binder uiBinder,
                      AppIdNamespaceRenderer appIdNamespaceRenderer,
                      AppIdRenderer appIdRenderer,
                      AppConstants appConstants,
                      NameSpaceValueSetter nameSpaceValueSetter,
                      DropdownFactory dropdownFactory,
                      KeyPropertyEditorDropdownResources dropdownResources,
                      @Assisted String key,
                      @Assisted JSONValue property,
                      @Assisted FetchKindsRunner fetchKindsRunner,
                      @Assisted FetchNamespacesRunner fetchNamespacesRunner) {
        super(key);

        this.appConstants = appConstants;
        this.property = property;
        this.nameSpaceValueSetter = nameSpaceValueSetter;

        appIdNamespace = dropdownFactory.create(appIdRenderer, dropdownResources);
        namespace = dropdownFactory.create(appIdNamespaceRenderer, dropdownResources);
        kind = dropdownFactory.create(new StringRenderer(), dropdownResources);

        fetchNamespaces(fetchNamespacesRunner);
        fetchKinds(fetchKindsRunner);

        initFormWidget(uiBinder.createAndBindUi(this));
        setInitialValue();
    }

    @Override
    public void onNamespacesFetched(List<AppIdNamespaceDto> namespaces) {
        setNamespaces(namespaces);
    }

    @Override
    public void onKindsFetched(List<String> kinds) {
        kind.clear();
        kind.addValue("<null>");

        for (String kind : kinds) {
            this.kind.addValue(kind);
        }

        setSelectedKind(key.getKind());
    }

    @Override
    public JSONValue getJsonValue() {
        JSONObject object = getValue().asJsonObject();

        return parseJsonValueWithMetadata(object, PropertyType.KEY, PropertyUtil.isPropertyIndexed(property));
    }

    @Override
    protected void showErrors() {
        showError(appConstants.invalidProtocolOrHost());
    }

    private void setNamespaces(List<AppIdNamespaceDto> namespaces) {
        List<Dropdown<AppIdNamespaceDto>> listboxes = Lists.newArrayList(namespace, appIdNamespace);

        nameSpaceValueSetter.setNamespace(namespaces, key.getAppIdNamespace(), listboxes);
    }

    private Key getValue() {
        return new Key(kind.getValue(), Strings.emptyToNull(name.getText()),
                Strings.emptyToNull(appId.getText()), id.getValue(),
                new AppIdNamespaceDto(appIdNamespace.getValue().getAppId(), namespace.getValue().getNamespace()),
                key.getParentKey());
    }

    private void setValue(Key key) {
        this.key = key;

        name.setText(key.getName());
        appId.setText(key.getAppId());
        id.setValue(key.getId());
    }

    private void setInitialValue() {
        JSONObject keyObject = PropertyUtil.getPropertyValue(property).isObject();

        setValue(Key.fromJsonObject(keyObject));
    }

    private void fetchNamespaces(FetchNamespacesRunner fetchNamespacesRunner) {
        AppIdNamespaceDto loading = new AppIdNamespaceDto("", "Loading namespaces...");
        namespace.setValue(loading);
        appIdNamespace.setValue(loading);

        fetchNamespacesRunner.fetch(this);
    }

    private void fetchKinds(FetchKindsRunner fetchKindsRunner) {
        String loading = "Loading kinds...";
        kind.clear();
        kind.addValue(loading);

        fetchKindsRunner.fetch(this);
    }

    private void setSelectedKind(String kind) {
        this.kind.setValue(kind);
    }
}
