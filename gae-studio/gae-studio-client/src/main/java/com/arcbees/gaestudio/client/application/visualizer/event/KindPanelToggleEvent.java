/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class KindPanelToggleEvent extends GwtEvent<KindPanelToggleEvent.KindPanelToggleHandler> {
    public interface KindPanelToggleHandler extends EventHandler {
        public void onKindPanelToggle(KindPanelToggleEvent event);
    }

    public enum Action {
        CLOSE, OPEN
    }

    public static Type<KindPanelToggleHandler> getType() {
        return TYPE;
    }

    private static final Type<KindPanelToggleHandler> TYPE = new Type<KindPanelToggleHandler>();

    private final Action action;

    public KindPanelToggleEvent(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public Type<KindPanelToggleHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(KindPanelToggleHandler handler) {
        handler.onKindPanelToggle(this);
    }
}
