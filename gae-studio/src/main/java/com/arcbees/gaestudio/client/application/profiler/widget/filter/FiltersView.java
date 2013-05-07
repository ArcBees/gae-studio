/*
 * Copyright 2013 ArcBees Inc.
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

import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class FiltersView extends ViewWithUiHandlers<FiltersUiHandlers> implements FiltersPresenter.MyView,
        ChangeHandler {
    public interface Binder extends UiBinder<Widget, FiltersView> {
    }

    @UiField(provided = true)
    AppResources resources;
    @UiField
    SimplePanel request;
    @UiField
    SimplePanel method;
    @UiField
    ListBox filters;
    @UiField
    SimplePanel type;

    private final AppConstants myConstants;
    private Filter currentlySelectedFilter = Filter.REQUEST;

    @Inject
    public FiltersView(final Binder uiBinder, final AppResources resources, final AppConstants myConstants) {
        this.resources = resources;
        this.myConstants = myConstants;

        initWidget(uiBinder.createAndBindUi(this));
        initFilters();
    }

    @Override
    public void onChange(ChangeEvent event) {
        int selectedIndex = filters.getSelectedIndex();
        Filter filter = Filter.valueOf(filters.getValue(selectedIndex));
        selectFilter(filter);
    }

    @Override
    public Filter getCurrentlyDisplayedFilter() {
        return currentlySelectedFilter;
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == FiltersPresenter.TYPE_SetRequestFilter) {
            request.setWidget(content);
        } else if (slot == FiltersPresenter.TYPE_MethodFilter) {
            method.setWidget(content);
        } else if (slot == FiltersPresenter.TYPE_TypeFilter) {
            type.setWidget(content);
        }
    }

    private void initFilters() {
        filters.addItem(myConstants.filterByRequest(), Filter.REQUEST.toString());
        filters.addItem(myConstants.filterByMethod(), Filter.METHOD.toString());
        filters.addItem(myConstants.filterByType(), Filter.TYPE.toString());
        filters.addChangeHandler(this);
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