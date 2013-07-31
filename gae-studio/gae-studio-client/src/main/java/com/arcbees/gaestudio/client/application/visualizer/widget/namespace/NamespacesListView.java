/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.namespace;

import java.util.List;

import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class NamespacesListView extends PopupViewWithUiHandlers<NamespacesListUiHandlers>
        implements NamespacesListPresenter.MyView {
    interface Binder extends UiBinder<Widget, NamespacesListView> {
    }

    @UiField(provided = true)
    ValueListBox<AppIdNamespaceDto> namespaces;
    @UiField
    Button delete;

    @Inject
    NamespacesListView(Binder uiBinder,
                       EventBus eventBus) {
        super(eventBus);

        this.namespaces = new ValueListBox<AppIdNamespaceDto>(new AppIdNamespaceRenderer());

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayNamespaces(List<AppIdNamespaceDto> namespaces) {
        this.namespaces.setAcceptableValues(namespaces);
        this.namespaces.setValue(null);
    }

    @UiHandler("delete")
    void onDeleted(ClickEvent event) {
        getUiHandlers().deleteAllFromNamespace(namespaces.getValue());
    }
}
