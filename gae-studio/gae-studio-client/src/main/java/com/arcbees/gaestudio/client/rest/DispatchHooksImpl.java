/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.rest;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.widget.ajax.LoadingEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.http.client.Response;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatchHooks;
import com.gwtplatform.dispatch.rest.shared.RestAction;

import static com.arcbees.gaestudio.client.application.widget.ajax.LoadingEvent.Action.BEGIN;
import static com.arcbees.gaestudio.client.application.widget.ajax.LoadingEvent.Action.END;

public class DispatchHooksImpl implements RestDispatchHooks, HasHandlers {
    private final EventBus eventBus;

    @Inject
    DispatchHooksImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onSuccess(RestAction restAction, Response response, Object o) {
        LoadingEvent.fire(this, END);
    }

    @Override
    public void onExecute(RestAction restAction) {
        LoadingEvent.fire(this, BEGIN);
    }

    @Override
    public void onFailure(RestAction restAction, Response response, Throwable throwable) {
        LoadingEvent.fire(this, END);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }
}
