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

import com.arcbees.chosen.client.ChosenOptions;
import com.arcbees.chosen.client.gwt.ChosenListBox;
import com.arcbees.gaestudio.client.application.widget.dropdown.Dropdown;
import com.arcbees.gaestudio.client.application.widget.dropdown.DropdownFactory;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.ChosenResources;
import com.arcbees.gaestudio.client.resources.KeyPropertyEditorDropdownResources;
import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.common.base.Strings;
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
    ChosenListBox appIdNamespace;
    @UiField(provided = true)
    ChosenListBox namespace;
    @UiField(provided = true)
    Dropdown<String> kind;
    @UiField
    TextBox name;
    @UiField(provided = true)
    RawPropertyEditor parentKey;

    private final NamespaceRenderer namespaceRenderer;
    private final AppIdRenderer appIdRenderer;
    private final AppConstants appConstants;
    private final JSONValue property;
    private final NameSpaceValueSetter nameSpaceValueSetter;

    private Key key;

    @Inject
    KeyPropertyEditor(
            Binder uiBinder,
            NamespaceRenderer namespaceRenderer,
            AppIdRenderer appIdRenderer,
            AppConstants appConstants,
            PropertyEditorsFactory propertyEditorsFactory,
            NameSpaceValueSetter nameSpaceValueSetter,
            DropdownFactory dropdownFactory,
            KeyPropertyEditorDropdownResources dropdownResources,
            ChosenResources chosenResources,
            @Assisted String key,
            @Assisted JSONValue property,
            @Assisted FetchKindsRunner fetchKindsRunner,
            @Assisted FetchNamespacesRunner fetchNamespacesRunner) {
        super(key);

        this.namespaceRenderer = namespaceRenderer;
        this.appIdRenderer = appIdRenderer;
        this.appConstants = appConstants;
        this.property = property;
        this.nameSpaceValueSetter = nameSpaceValueSetter;

        parentKey = (RawPropertyEditor) propertyEditorsFactory
                .createRawEditor(PropertyName.PARENT_KEY, property.isObject().get(PropertyName.PARENT_KEY));

        ChosenOptions options = new ChosenOptions();
        options.setDisableSearchThreshold(10);
        options.setResources(chosenResources);

        appIdNamespace = new ChosenListBox(options);
        namespace = new ChosenListBox(options);
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
        nameSpaceValueSetter.setNamespace(namespaces, key.getAppIdNamespace(), namespace, namespaceRenderer);
        nameSpaceValueSetter.setNamespace(namespaces, key.getAppIdNamespace(), appIdNamespace, appIdRenderer);
    }

    private Key getValue() {
        Key parentKey = null;
        JSONValue parentKeyObject = this.parentKey.getJsonValue();
        if (parentKeyObject != null && parentKeyObject.isObject() != null) {
            parentKey = Key.fromJsonObject(parentKeyObject.isObject());
        }

        return new Key(kind.getValue(), Strings.emptyToNull(name.getText()),
                Strings.emptyToNull(appId.getText()), id.getValue(),
                new AppIdNamespaceDto(appId.getValue(), namespace.getValue()),
                parentKey);
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
