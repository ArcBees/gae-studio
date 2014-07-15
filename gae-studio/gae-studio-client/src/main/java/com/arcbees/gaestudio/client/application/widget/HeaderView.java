/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.widget;

import com.arcbees.analytics.client.universalanalytics.UniversalAnalytics;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.client.resources.WidgetResources;
import com.google.gwt.dom.client.AnchorElement;
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

public class HeaderView extends ViewWithUiHandlers<HeaderUiHandlers> implements HeaderPresenter.MyView {
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

    private final String activeStyleName;

    @Inject
    HeaderView(Binder uiBinder,
               WidgetResources widgetRes,
               AppResources resources,
               final UniversalAnalytics universalAnalytics) {
        this.widgetRes = widgetRes;
        this.resources = resources;

        initWidget(uiBinder.createAndBindUi(this));

        activeStyleName = widgetRes.header().activeState();

        $(logoAnchor).click(new Function() {
            @Override
            public void f() {
                universalAnalytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Header -> Logo");
            }
        });
    }

    @Override
    public void setProfilerActive() {
        $("." + activeStyleName).removeClass(activeStyleName);
    }

    @UiHandler("report")
    void handleClick(ClickEvent event) {
        getUiHandlers().supportClicked();
    }
}
