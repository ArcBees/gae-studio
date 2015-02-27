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
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class SetStateFromPlaceRequestEvent
        extends GwtEvent<SetStateFromPlaceRequestEvent.SetStateFromPlaceRequestHandler> {
    public interface SetStateFromPlaceRequestHandler extends EventHandler {
        void setStateFromPlaceRequest(SetStateFromPlaceRequestEvent event);
    }

    private static final Type<SetStateFromPlaceRequestHandler> TYPE = new Type<>();

    private final PlaceRequest placeRequest;

    private SetStateFromPlaceRequestEvent(PlaceRequest placeRequest) {
        this.placeRequest = placeRequest;
    }

    public static void fire(HasHandlers source, PlaceRequest placeRequest) {
        source.fireEvent(new SetStateFromPlaceRequestEvent(placeRequest));
    }

    public static Type<SetStateFromPlaceRequestHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<SetStateFromPlaceRequestHandler> getAssociatedType() {
        return TYPE;
    }

    public PlaceRequest getPlaceRequest() {
        return placeRequest;
    }

    @Override
    protected void dispatch(SetStateFromPlaceRequestHandler handler) {
        handler.setStateFromPlaceRequest(this);
    }
}
