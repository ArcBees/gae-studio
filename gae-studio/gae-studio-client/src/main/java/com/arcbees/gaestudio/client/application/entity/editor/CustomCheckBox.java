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

package com.arcbees.gaestudio.client.application.entity.editor;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.$;

public class CustomCheckBox implements IsWidget, AttachEvent.Handler {
    interface Binder extends UiBinder<Widget, CustomCheckBox> {
    }

    private final Widget widget;
    private final AppResources appResources;

    @Inject
    CustomCheckBox(
            Binder uiBinder,
            AppResources appResources) {
        widget = uiBinder.createAndBindUi(this);

        this.appResources = appResources;

        asWidget().addAttachHandler(this);
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached()) {
            $(widget).click(new Function() {
                @Override
                public boolean f(Event e) {
                    toggleChecked();

                    return true;
                }
            });
        }
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    public boolean getValue() {
        return $(widget).hasClass(appResources.styles().checked());
    }

    public void setValue(boolean checked) {
        if (checked) {
            $(widget).addClass(appResources.styles().checked());
            $(widget).html("✓");
        } else {
            $(widget).removeClass(appResources.styles().checked());
            $(widget).html("");
        }
    }

    private void toggleChecked() {
        setValue(!getValue());
    }
}
