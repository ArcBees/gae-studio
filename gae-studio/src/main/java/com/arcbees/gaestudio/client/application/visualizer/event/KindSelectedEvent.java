/**
 * Copyright 2013 ArcBees Inc.
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
import com.google.web.bindery.event.shared.HandlerRegistration;

public class KindSelectedEvent extends GwtEvent<KindSelectedEvent.KindSelectedHandler> {
    private String kind;

    protected KindSelectedEvent() {
        // Possibly for serialization.
    }

    public KindSelectedEvent(String kind) {
        this.kind = kind;
    }

    public static void fire(HasHandlers source, String kind) {
        KindSelectedEvent eventInstance = new KindSelectedEvent(kind);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, KindSelectedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasKindSelectedHandlers extends HasHandlers {
        HandlerRegistration addKindSelectedHandler(KindSelectedHandler handler);
    }

    public interface KindSelectedHandler extends EventHandler {
        public void onKindSelected(KindSelectedEvent event);
    }

    private static final Type<KindSelectedHandler> TYPE = new Type<KindSelectedHandler>();

    public static Type<KindSelectedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<KindSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(KindSelectedHandler handler) {
        handler.onKindSelected(this);
    }

    public String getKind() {
        return kind;
    }
}
