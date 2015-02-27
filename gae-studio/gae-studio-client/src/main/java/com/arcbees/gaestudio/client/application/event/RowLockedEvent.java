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

public class RowLockedEvent extends GwtEvent<RowLockedEvent.RowLockedHandler> {
    public interface RowLockedHandler extends EventHandler {
        void onRowLocked(RowLockedEvent rowLockedEvent);
    }

    private static final Type<RowLockedHandler> TYPE = new Type<>();

    private RowLockedEvent() {
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new RowLockedEvent());
    }

    public static Type<RowLockedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<RowLockedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RowLockedHandler handler) {
        handler.onRowLocked(this);
    }
}
