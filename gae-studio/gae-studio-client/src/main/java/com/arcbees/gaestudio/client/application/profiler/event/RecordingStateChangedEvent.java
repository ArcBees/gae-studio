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

package com.arcbees.gaestudio.client.application.profiler.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class RecordingStateChangedEvent extends GwtEvent<RecordingStateChangedEvent.RecordingStateChangedHandler> {
    public interface RecordingStateChangedHandler extends EventHandler {
        void onRecordingStateChanged(RecordingStateChangedEvent event);
    }

    private static final Type<RecordingStateChangedHandler> TYPE = new Type<RecordingStateChangedHandler>();

    private boolean starting;

    public RecordingStateChangedEvent(boolean starting) {
        this.starting = starting;
    }

    public static void fire(HasHandlers source, boolean starting) {
        RecordingStateChangedEvent eventInstance = new RecordingStateChangedEvent(starting);
        source.fireEvent(eventInstance);
    }

    public static Type<RecordingStateChangedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<RecordingStateChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(RecordingStateChangedHandler handler) {
        handler.onRecordingStateChanged(this);
    }

    public boolean isStarting() {
        return starting;
    }
}
