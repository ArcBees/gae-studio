/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.widget.ajax;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class LoadingEvent extends GwtEvent<LoadingEvent.LoadingEventHandler> {
    public enum Action {
        BEGIN, END
    }

    public interface LoadingEventHandler extends EventHandler {
        public void onLoadingEvent(LoadingEvent event);
    }

    private static final Type<LoadingEventHandler> TYPE = new Type<>();

    public static Type<LoadingEventHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, Action action) {
        source.fireEvent(new LoadingEvent(action));
    }

    private final Action action;

    public Action getAction() {
        return action;
    }

    private LoadingEvent(Action action) {
        this.action = action;
    }

    @Override
    public Type<LoadingEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LoadingEventHandler handler) {
        handler.onLoadingEvent(this);
    }
}
