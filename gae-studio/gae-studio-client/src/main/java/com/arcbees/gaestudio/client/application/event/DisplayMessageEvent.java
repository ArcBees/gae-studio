/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.event;

import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class DisplayMessageEvent extends GwtEvent<DisplayMessageEvent.DisplayMessageHandler> {
    private Message message;

    protected DisplayMessageEvent() {
        // Possibly for serialization.
    }

    public DisplayMessageEvent(Message message) {
        this.message = message;
    }

    public static void fire(HasHandlers source, Message message) {
        DisplayMessageEvent eventInstance = new DisplayMessageEvent(message);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, DisplayMessageEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasDisplayMessageHandlers extends HasHandlers {
        HandlerRegistration addDisplayMessageHandler(DisplayMessageHandler handler);
    }

    public interface DisplayMessageHandler extends EventHandler {
        public void onDisplayMessage(DisplayMessageEvent event);
    }

    private static final Type<DisplayMessageHandler> TYPE = new Type<DisplayMessageHandler>();

    public static Type<DisplayMessageHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<DisplayMessageHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DisplayMessageHandler handler) {
        handler.onDisplayMessage(this);
    }

    public Message getMessage() {
        return message;
    }
}
