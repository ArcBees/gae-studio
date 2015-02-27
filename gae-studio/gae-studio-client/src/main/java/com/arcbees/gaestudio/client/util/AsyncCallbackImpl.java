/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
