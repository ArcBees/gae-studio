/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.widget.namespace.AppIdRenderer;
import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.common.base.Strings;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasConstrainedValue;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.LongBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.parseJsonValueWithMetadata;

public class KeyPropertyEditor extends AbstractPropertyEditor<Key>
        implements FetchNamespacesRunner.FetchNamespacesCallback, FetchKindsRunner.FetchKindsCallback {
    static class AppIdNamespaceRenderer extends AbstractRenderer<AppIdNamespaceDto> {
        @Override
        public String render(AppIdNamespaceDto object) {
            if (object == null) {
                return "<null>";
            } else if (Strings.isNullOrEmpty(object.getNamespace())) {
                return "<default>";
            }

            return object.getNamespace();
        }
    }

    interface Binder extends UiBinder<Widget, KeyPropertyEditor> {
    }

    @UiField
    LongBox id;
    @UiField
    TextBox appId;
    @UiField(provided = true)
    ValueListBox<AppIdNamespaceDto> appIdNamespace;
    @UiField(provided = true)
    ValueListBox<AppIdNamespaceDto> namespace;
    @UiField(provided = true)
    ListBox kind;
    @UiField
    TextBox name;
    @UiField(provided = true)
    RawPropertyEditor parentKey;

    private final AppConstants appConstants;
    private final JSONValue property;
    private final NameSpaceValueSetter nameSpaceValueSetter;

    private Key key;

    @Inject
    KeyPropertyEditor(Binder uiBinder,
                      AppIdNamespaceRenderer appIdNamespaceRenderer,
                      AppIdRenderer appIdRenderer,
                      AppConstants appConstants,
                      PropertyEditorFactory propertyEditorFactory,
                      NameSpaceValueSetter nameSpaceValueSetter,
                      @Assisted String key,
                      @Assisted JSONValue property,
                      @Assisted FetchKindsRunner fetchKindsRunner,
                      @Assisted FetchNamespacesRunner fetchNamespacesRunner) {
        super(key);

        this.appConstants = appConstants;
        this.property = property;
        this.nameSpaceValueSetter = nameSpaceValueSetter;

        parentKey = (RawPropertyEditor) propertyEditorFactory
                .createRawEditor(PropertyName.PARENT_KEY, property.isObject().get(PropertyName.PARENT_KEY));

        appIdNamespace = new ValueListBox<AppIdNamespaceDto>(appIdRenderer);
        namespace = new ValueListBox<AppIdNamespaceDto>(appIdNamespaceRenderer);
        kind = new ListBox();

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
        kind.addItem("<null>", (String) null);

        for (String kind : kinds) {
            this.kind.addItem(kind, kind);
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
        List<HasConstrainedValue<AppIdNamespaceDto>> listboxes =
                new ArrayList<HasConstrainedValue<AppIdNamespaceDto>>();
        listboxes.add(namespace);
        listboxes.add(appIdNamespace);

        nameSpaceValueSetter.setNamespace(namespaces, key.getAppIdNamespace(), listboxes);
    }

    private Key getValue() {
        Key parentKey = null;
        JSONValue parentKeyObject = this.parentKey.getJsonValue();
        if (parentKeyObject != null && parentKeyObject.isObject() != null) {
            parentKey = Key.fromJsonObject(parentKeyObject.isObject());
        }

        return new Key(kind.getValue(kind.getSelectedIndex()), Strings.emptyToNull(name.getText()),
                Strings.emptyToNull(appId.getText()), id.getValue(),
                new AppIdNamespaceDto(appIdNamespace.getValue().getAppId(), namespace.getValue().getNamespace()),
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
        AppIdNamespaceDto loading = new AppIdNamespaceDto("", "Loading namespaces...");
        namespace.setValue(loading);
        appIdNamespace.setValue(loading);

        fetchNamespacesRunner.fetch(this);
    }

    private void fetchKinds(FetchKindsRunner fetchKindsRunner) {
        String loading = "Loading kinds...";
        kind.clear();
        kind.addItem(loading);

        fetchKindsRunner.fetch(this);
    }

    private void setSelectedKind(String kind) {
        for (int i = 0; i < this.kind.getItemCount(); i++) {
            String item = this.kind.getValue(i);
            if (item.equals(kind)) {
                this.kind.setSelectedIndex(i);
                break;
            }
        }
    }
}
