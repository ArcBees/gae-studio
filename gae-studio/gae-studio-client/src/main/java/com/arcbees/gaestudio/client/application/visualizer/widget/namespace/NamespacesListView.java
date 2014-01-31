/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.namespace;

import java.util.List;

import com.arcbees.gaestudio.client.application.widget.dropdown.Dropdown;
import com.arcbees.gaestudio.client.application.widget.dropdown.DropdownFactory;
import com.arcbees.gaestudio.client.resources.NamespaceDropdownResources;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class NamespacesListView extends PopupViewWithUiHandlers<NamespacesListUiHandlers>
        implements NamespacesListPresenter.MyView {
    interface Binder extends UiBinder<Widget, NamespacesListView> {
    }

    @UiField
    Button delete;
    @UiField(provided = true)
    Dropdown<AppIdNamespaceDto> dropdown;

    @Inject
    NamespacesListView(Binder uiBinder,
                       EventBus eventBus,
                       DropdownFactory dropdownFactory,
                       NamespaceDropdownResources dropdownResources,
                       AppIdNamespaceRenderer appIdNamespaceRenderer) {
        super(eventBus);

        this.dropdown = dropdownFactory.create(appIdNamespaceRenderer, dropdownResources);

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayNamespaces(List<AppIdNamespaceDto> namespaces) {
        dropdown.clear();

        dropdown.addValue(null);

        for (AppIdNamespaceDto namespace : namespaces) {
            dropdown.addValue(namespace);
        }
    }

    @UiHandler("delete")
    void onDeleted(ClickEvent event) {
        getUiHandlers().deleteAllFromNamespace(dropdown.getValue());
    }
}
