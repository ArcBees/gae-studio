/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ToolbarToggleEvent extends GwtEvent<ToolbarToggleEvent.ToolbarToggleHandler> {
    public interface ToolbarToggleHandler extends EventHandler {
        public void onToolbarToggle(ToolbarToggleEvent event);
    }

    public static void fire(HasHandlers source, boolean open) {
        source.fireEvent(new ToolbarToggleEvent(open));
    }

    public static Type<ToolbarToggleHandler> getType() {
        return TYPE;
    }

    private static final Type<ToolbarToggleHandler> TYPE = new Type<ToolbarToggleHandler>();

    private final boolean open;

    private ToolbarToggleEvent(boolean open) {
        this.open = open;
    }

    @Override
    public Type<ToolbarToggleHandler> getAssociatedType() {
        return TYPE;
    }

    @SuppressWarnings("UnusedDeclaration")
    public boolean isOpen() {
        return open;
    }

    @Override
    protected void dispatch(ToolbarToggleHandler handler) {
        handler.onToolbarToggle(this);
    }
}
