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
    DispatchHooksImpl(
            EventBus eventBus) {
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
