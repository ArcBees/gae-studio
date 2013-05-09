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

package com.arcbees.gaestudio.client.application.visualizer.ui;

import javax.inject.Inject;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.AttachEvent;
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
        Integer maxHeight = Integer.valueOf($(jsonContent).css("max-height").replace("px", ""));
        more.setVisible($(jsonContent).height() == maxHeight);
    }

    private String prettifyJson(String json) {
        return stringify(json).replace("\n", "<br/>").replace("\\\"", "\"").replace(" ", "&nbsp;");
    }

    private native String stringify(String json) /*-{
        var jsonValue = JSON.parse(json);
        return JSON.stringify(jsonValue.propertyMap, null, 4);
    }-*/;
}
