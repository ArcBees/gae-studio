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

package com.arcbees.gaestudio.client.application.widget;

import com.arcbees.analytics.shared.Analytics;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.WidgetResources;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static com.arcbees.gaestudio.client.application.analytics.EventCategories.UI_ELEMENTS;
import static com.google.gwt.query.client.GQuery.$;

public class HeaderView extends ViewWithUiHandlers<HeaderUiHandlers>
        implements HeaderPresenter.MyView {
    interface Binder extends UiBinder<Widget, HeaderView> {
    }

    @UiField(provided = true)
    WidgetResources widgetRes;
    @UiField(provided = true)
    AppResources resources;
    @UiField
    AnchorElement logoAnchor;
    @UiField
    Button report;
    @UiField
    DivElement ajaxLoader;
    @UiField
    DivElement menu;
    @UiField
    AnchorElement profilerAnchor;

    private final String activeStyleName;
    private final Analytics analytics;

    @Inject
    HeaderView(
            Binder uiBinder,
            WidgetResources widgetRes,
            AppResources resources,
            final Analytics analytics) {
        this.widgetRes = widgetRes;
        this.resources = resources;
        this.analytics = analytics;

        initWidget(uiBinder.createAndBindUi(this));

        activeStyleName = widgetRes.header().activeState();

        $(logoAnchor).click(new Function() {
            @Override
            public void f() {
                analytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Header -> Logo");
            }
        });

        $("a", menu).click(new Function() {
            @Override
            public void f() {
                $("." + activeStyleName).removeClass(activeStyleName);
                $(getElement()).addClass(activeStyleName);
            }
        });
    }

    @Override
    public void setProfilerActive() {
        $("." + activeStyleName).removeClass(activeStyleName);
        $(profilerAnchor).addClass(activeStyleName);
    }

    @Override
    public void hideLoadingBar() {
        ajaxLoader.getStyle().setVisibility(Style.Visibility.HIDDEN);
    }

    @Override
    public void displayLoadingBar() {
        ajaxLoader.getStyle().setVisibility(Style.Visibility.VISIBLE);
    }

    @UiHandler("report")
    void handleClick(ClickEvent event) {
        getUiHandlers().supportClicked();

        analytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Header -> Submit Issue");
    }
}
