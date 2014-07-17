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

public class DeselectEvent extends GwtEvent<DeselectEvent.DeselectHandler> {
    public interface DeselectHandler extends EventHandler {
        public void onDeselectEntities(DeselectEvent event);
    }

    public static Type<DeselectHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new DeselectEvent());
    }

    private static final Type<DeselectHandler> TYPE = new Type<DeselectHandler>();

    @Override
    public Type<DeselectHandler> getAssociatedType() {
        return TYPE;
    }

    DeselectEvent() {
    }

    @Override
    protected void dispatch(DeselectHandler handler) {
        handler.onDeselectEntities(this);
    }
}
