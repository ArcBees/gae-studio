/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.gaestudio.client.application.widget.dropdown.Dropdown;
import com.arcbees.gaestudio.client.application.widget.dropdown.DropdownFactory;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.FilterDropdownResources;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class FiltersView extends ViewWithUiHandlers<FiltersUiHandlers>
        implements FiltersPresenter.MyView, ValueChangeHandler<Filter> {
    interface Binder extends UiBinder<Widget, FiltersView> {
    }

    @UiField(provided = true)
    AppResources resources;
    @UiField
    SimplePanel request;
    @UiField
    SimplePanel method;
    @UiField
    SimplePanel type;
    @UiField(provided = true)
    Dropdown<Filter> dropdown;

    private Filter currentlySelectedFilter = Filter.REQUEST;

    @Inject
    FiltersView(
            Binder uiBinder,
            AppResources resources,
            DropdownFactory dropdownFactory,
            FilterDropdownResources dropdownResources,
            FilterRenderer renderer) {
        this.resources = resources;

        this.dropdown = dropdownFactory.create(renderer, dropdownResources);

        dropdown.addValue(Filter.REQUEST);
        dropdown.addValue(Filter.TYPE);
        dropdown.addValue(Filter.METHOD);

        dropdown.addValueChangeHandler(this);

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public Filter getCurrentlyDisplayedFilter() {
        return currentlySelectedFilter;
    }

    @Override
    public void onValueChange(ValueChangeEvent<Filter> event) {
        selectFilter(event.getValue());
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == FiltersPresenter.SLOT_REQUEST_FILTER) {
            request.setWidget(content);
        } else if (slot == FiltersPresenter.SLOT_METHOD_FILTER) {
            method.setWidget(content);
        } else if (slot == FiltersPresenter.SLOT_TYPE_FILTER) {
            type.setWidget(content);
        }
    }

    private void selectFilter(Filter filter) {
        if (filter != currentlySelectedFilter) {
            getPanelFromFilter(currentlySelectedFilter).setVisible(false);
            currentlySelectedFilter = filter;
            getUiHandlers().changeFilter();
            getPanelFromFilter(filter).setVisible(true);
        }
    }

    private Widget getPanelFromFilter(Filter filter) {
        switch (filter) {
            case METHOD:
                return method;
            case TYPE:
                return type;
            default:
                return request;
        }
    }
}
