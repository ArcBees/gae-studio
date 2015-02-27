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

package com.arcbees.gaestudio.client.application.visualizer.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ToolbarToggleEvent extends GwtEvent<ToolbarToggleEvent.ToolbarToggleHandler> {
    public interface ToolbarToggleHandler extends EventHandler {
        void onToolbarToggle(ToolbarToggleEvent event);
    }

    private static final Type<ToolbarToggleHandler> TYPE = new Type<ToolbarToggleHandler>();

    private final boolean open;

    private ToolbarToggleEvent(boolean open) {
        this.open = open;
    }

    public static void fire(HasHandlers source, boolean open) {
        source.fireEvent(new ToolbarToggleEvent(open));
    }

    public static Type<ToolbarToggleHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<ToolbarToggleHandler> getAssociatedType() {
        return TYPE;
    }

    @SuppressWarnings("UnusedDeclaration")
    public boolean isOpen() {
        return open;
    }

    @Override
    protected void dispatch(ToolbarToggleHandler handler) {
        handler.onToolbarToggle(this);
    }
}
