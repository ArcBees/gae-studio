/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.gaestudio.client.Resources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;

public abstract class BaseLabel extends Label {

    private final Resources resources;

    protected BaseLabel(final Resources resources, final Long id, final LabelCallback callback) {
        this.resources = resources;

        addStyleName(resources.styles().item());
        addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                callback.onClick(BaseLabel.this, id);
            }
        });
    }

    public void setSelected(boolean selected) {
        setStyleName(resources.styles().selected(), selected);
    }

}
