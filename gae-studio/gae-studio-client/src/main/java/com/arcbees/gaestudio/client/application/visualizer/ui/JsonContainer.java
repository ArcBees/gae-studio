/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.ui;

import javax.inject.Inject;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.query.client.Properties;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.google.gwt.core.client.Scheduler.ScheduledCommand;
import static com.google.gwt.query.client.GQuery.$;

public class JsonContainer extends Composite implements AttachEvent.Handler {
    interface Binder extends UiBinder<Widget, JsonContainer> {
    }

    @UiField
    HTML jsonContent;
    @UiField
    HTMLPanel container;
    @UiField
    Label more;

    @Inject
    JsonContainer(Binder binder,
                  @Assisted String jsonContent) {
        initWidget(binder.createAndBindUi(this));

        this.jsonContent.setHTML(prettifyJson(jsonContent));
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached()) {
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                @Override
                public void execute() {
                    maybeShowHasMore();
                }
            });
        }
    }

    private void maybeShowHasMore() {
        Integer maxHeight = (int) $(jsonContent).cur("max-height", true);
        more.setVisible($(jsonContent).height() == maxHeight);
    }

    private String prettifyJson(String json) {
        return stringify(json).replace("\n", "<br/>").replace("\\\"", "\"").replace(" ", "&nbsp;");
    }

    private String stringify(String json) {
        Properties p = JsUtils.parseJSON(json);
        return stringify(p.getJavaScriptObject("propertyMap"));
    }

    private native String stringify(JavaScriptObject jso) /*-{
        return JSON.stringify(jso, null, 4);
    }-*/;
}
