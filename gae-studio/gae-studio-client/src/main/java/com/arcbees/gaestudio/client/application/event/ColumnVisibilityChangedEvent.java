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

package com.arcbees.gaestudio.client.application.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ColumnVisibilityChangedEvent
        extends GwtEvent<ColumnVisibilityChangedEvent.ColumnVisibilityChangedHandler> {
    public interface ColumnVisibilityChangedHandler extends EventHandler {
        void onColumnVisibilityChanged(ColumnVisibilityChangedEvent event);
    }

    private static final Type<ColumnVisibilityChangedHandler> TYPE = new Type<>();

    private ColumnVisibilityChangedEvent() {
    }

    public static void fire(HasHandlers source) {
        ColumnVisibilityChangedEvent eventInstance = new ColumnVisibilityChangedEvent();
        source.fireEvent(eventInstance);
    }

    public static Type<ColumnVisibilityChangedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<ColumnVisibilityChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ColumnVisibilityChangedHandler handler) {
        handler.onColumnVisibilityChanged(this);
    }
}
