/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;

public class SetStateFromPlaceRequestEvent
        extends GwtEvent<SetStateFromPlaceRequestEvent.SetStateFromPlaceRequestHandler> {
    public interface SetStateFromPlaceRequestHandler extends EventHandler {
        public void setStateFromPlaceRequest(SetStateFromPlaceRequestEvent event);
    }

    private static final Type<SetStateFromPlaceRequestHandler> TYPE = new Type<>();

    public static void fire(HasHandlers source, PlaceRequest placeRequest) {
        source.fireEvent(new SetStateFromPlaceRequestEvent(placeRequest));
    }

    private final PlaceRequest placeRequest;

    private SetStateFromPlaceRequestEvent(PlaceRequest placeRequest) {
        this.placeRequest = placeRequest;
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
