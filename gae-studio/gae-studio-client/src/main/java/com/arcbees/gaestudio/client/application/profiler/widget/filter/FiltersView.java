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
