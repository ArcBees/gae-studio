/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class FullScreenEvent extends GwtEvent<FullScreenEvent.FullScreenEventHandler> {
    public interface FullScreenEventHandler extends EventHandler {
        void onFullScreen(FullScreenEvent event);
    }

    public static Type<FullScreenEventHandler> getType() {
        return TYPE;
    }

    private static final Type<FullScreenEventHandler> TYPE = new Type<FullScreenEventHandler>();

    private final boolean activate;

    public FullScreenEvent(boolean activate) {
        this.activate = activate;
    }

    @Override
    public Type<FullScreenEventHandler> getAssociatedType() {
        return TYPE;
    }

    public boolean isActivate() {
        return activate;
    }

    @Override
    protected void dispatch(FullScreenEventHandler handler) {
        handler.onFullScreen(this);
    }
}
