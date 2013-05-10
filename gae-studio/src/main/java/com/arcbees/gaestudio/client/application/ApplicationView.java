/**
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

package com.arcbees.gaestudio.client.application;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

import javax.inject.Inject;

import static com.google.gwt.query.client.GQuery.$;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView, AttachEvent.Handler {
    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    SimplePanel header;
    @UiField
    SimpleLayoutPanel main;
    @UiField
    SimplePanel messages;

    private final String noOverflowStyleName;

    @Inject
    ApplicationView(Binder uiBinder,
                    AppResources appResources) {
        initWidget(uiBinder.createAndBindUi(this));

        noOverflowStyleName = appResources.styles().noOverflow();

        asWidget().addAttachHandler(this);
    }

    @Override
    public void onAttachOrDetach(AttachEvent attachEvent) {
        if (attachEvent.isAttached()) {
            bindGwtQuery();
        }
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ApplicationPresenter.TYPE_SetMainContent) {
            main.setWidget(content);
        } else if (slot == ApplicationPresenter.TYPE_SetHeaderContent) {
            header.setWidget(content);
        } else if (slot == ApplicationPresenter.TYPE_SetMessagesContent) {
            messages.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }

    private void bindGwtQuery() {
        $("." + noOverflowStyleName).css("overflow", "visible");
        $("." + noOverflowStyleName).parent("div").css("overflow", "visible");
        $("." + noOverflowStyleName).parents("div").css("overflow", "visible");
        $("." + noOverflowStyleName).parents("div").parents("div").css("overflow", "visible");
    }
}
