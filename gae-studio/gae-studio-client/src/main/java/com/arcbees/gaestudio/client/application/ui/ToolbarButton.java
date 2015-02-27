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

package com.arcbees.gaestudio.client.application.ui;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import static com.google.gwt.query.client.GQuery.$;

public class ToolbarButton implements AttachEvent.Handler, IsWidget {
    interface Binder extends UiBinder<Widget, ToolbarButton> {
    }

    @UiField(provided = true)
    AppResources resources;
    @UiField
    HTMLPanel button;
    @UiField
    ParagraphElement text;
    @UiField
    Element icon;

    private final ToolbarButtonCallback callback;
    private final Widget widget;

    private Function buttonClickFunction;

    @AssistedInject
    ToolbarButton(
            Binder binder,
            AppResources resources,
            @Assisted("text") String text,
            @Assisted("iconClass") String iconClass,
            @Assisted ToolbarButtonCallback callback) {
        this(binder, resources, text, iconClass, callback, "");
    }

    @AssistedInject
    ToolbarButton(
            Binder binder,
            AppResources resources,
            @Assisted("text") String text,
            @Assisted("iconClass") String iconClass,
            @Assisted ToolbarButtonCallback callback,
            @Assisted("debugId") String debugId) {
        this.resources = resources;

        widget = binder.createAndBindUi(this);

        this.text.setInnerSafeHtml(SafeHtmlUtils.fromString(text));

        icon.addClassName(iconClass);

        this.callback = callback;

        widget.ensureDebugId(debugId);

        widget.addAttachHandler(this);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached()) {
            register(callback);
        }
    }

    private void register(final ToolbarButtonCallback callback) {
        buttonClickFunction = new Function() {
            @Override
            public boolean f(Event e) {
                callback.onClicked();
                return true;
            }
        };

        setEnabled(!$(button).hasClass(resources.styles().toolbarButtonDisabled()));
    }

    public void setEnabled(Boolean enabled) {
        $(button).toggleClass(resources.styles().toolbarButtonDisabled(), !enabled);
        $(button).unbind(BrowserEvents.CLICK, buttonClickFunction);
        if (enabled) {
            $(button).click(buttonClickFunction);
        }
    }

    public void setAddStyleNames(String style) {
        asWidget().addStyleName(style);
    }
}
