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

import com.arcbees.chosen.client.ChosenOptions;
import com.arcbees.chosen.client.gwt.ChosenValueListBox;
import com.arcbees.gaestudio.client.resources.ChosenResources;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

import static com.google.gwt.query.client.GQuery.$;

public class NamespacesListView extends PopupViewWithUiHandlers<NamespacesListUiHandlers>
        implements NamespacesListPresenter.MyView, ValueChangeHandler<AppIdNamespaceDto> {
    interface Binder extends UiBinder<Widget, NamespacesListView> {
    }

    @UiField
    DivElement delete;
    @UiField(provided = true)
    ChosenValueListBox<AppIdNamespaceDto> namespacesDropdown;

    @Inject
    NamespacesListView(
            Binder uiBinder,
            EventBus eventBus,
            AppIdNamespaceRenderer appIdNamespaceRenderer,
            ChosenResources chosenResources) {
        super(eventBus);

        ChosenOptions options = new ChosenOptions();
        options.setResources(chosenResources);
        options.setDisableSearchThreshold(10);
        options.setSearchContains(true);

        namespacesDropdown = new ChosenValueListBox<>(appIdNamespaceRenderer, options);
        namespacesDropdown.addValueChangeHandler(this);

        initWidget(uiBinder.createAndBindUi(this));

        $(delete).click(new Function() {
            @Override
            public void f() {
                getUiHandlers().deleteAllFromNamespace(namespacesDropdown.getValue());
            }
        });
    }

    @Override
    public void displayNamespaces(List<AppIdNamespaceDto> namespaces) {
        AppIdNamespaceDto oldValue = namespacesDropdown.getValue();

        namespaces.add(null);

        namespacesDropdown.setAcceptableValues(namespaces);
        namespacesDropdown.setValue(oldValue, true);
    }

    @Override
    public void onValueChange(ValueChangeEvent<AppIdNamespaceDto> event) {
        getUiHandlers().dropdownValueChanged(event);
    }
}
