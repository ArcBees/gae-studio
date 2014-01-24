/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.util;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.event.DisplayMessageEvent;
import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageStyle;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

public abstract class AsyncCallbackImpl<T> implements AsyncCallback<T>, HasHandlers {
    @Inject
    private static EventBus eventBus;

    private final String failureMessage;

    public AsyncCallbackImpl() {
        failureMessage = null;
    }

    public AsyncCallbackImpl(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }

    public void handleFailure(Throwable caught) {
    }

    @Override
    public final void onFailure(Throwable caught) {
        if (failureMessage != null) {
            DisplayMessageEvent.fire(this, new Message(failureMessage, MessageStyle.ERROR));
        }

        handleFailure(caught);
    }
}
