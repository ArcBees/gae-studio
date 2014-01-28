/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.ui;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

public class PanelToggle implements ClickHandler, IsWidget {
    public enum Direction {
        LEFT, RIGHT
    }

    public interface ToggleHandler {
        void onToggle();
    }

    private static final String TRANSFORM = "webkitTransform";

    private final ToggleHandler toggleHandler;
    private final Image widget;

    private Direction direction = Direction.LEFT;

    @Inject
    PanelToggle(AppResources appResources,
                @Assisted ToggleHandler toggleHandler) {
        this.toggleHandler = toggleHandler;

        widget = new Image();

        widget.setResource(appResources.closeToggle());
        widget.addStyleName(appResources.styles().panelToggle());
        widget.addClickHandler(this);
    }

    public void setAddStyleNames(String stylename) {
        widget.addStyleName(stylename);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void onClick(ClickEvent event) {
        toggleHandler.onToggle();
        rotateToggle();
    }

    public Direction getDirection() {
        return direction;
    }

    private void rotateToggle() {
        if (isFlipped()) {
            this.direction = Direction.LEFT;
            setRotation(0);
        } else {
            this.direction = Direction.RIGHT;
            setRotation(180);
        }
    }

    private boolean isFlipped() {
        return getToggleTransform().equals(buildRotateString(180));
    }

    private String getToggleTransform() {
        Style style = widget.getElement().getStyle();
        return style.getProperty(TRANSFORM);
    }

    private void setRotation(int rotationInDegrees) {
        Style style = widget.getElement().getStyle();
        style.setProperty(TRANSFORM, buildRotateString(rotationInDegrees));
    }

    private String buildRotateString(int rotationInDegrees) {
        return "rotateY(" + rotationInDegrees + "deg)";
    }
}