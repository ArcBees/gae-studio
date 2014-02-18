/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

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
    CustomCheckBox(Binder uiBinder,
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

                    return  true;
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
        if(checked) {
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