/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
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
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class SetStateFromPlaceRequestEvent extends GwtEvent<SetStateFromPlaceRequestEvent.SetStateFromPlaceRequestHandler> {
    public interface SetStateFromPlaceRequestHandler extends EventHandler {
        public void setStateFromPlaceRequest(SetStateFromPlaceRequestEvent event);
    }

    private final PlaceRequest placeRequest;

    private SetStateFromPlaceRequestEvent(PlaceRequest placeRequest) {
        this.placeRequest = placeRequest;
    }

    public static void fire(HasHandlers source, PlaceRequest placeRequest) {
        fire(source, new SetStateFromPlaceRequestEvent(placeRequest));
    }

    public static void fire(HasHandlers source, SetStateFromPlaceRequestEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    private static final Type<SetStateFromPlaceRequestHandler> TYPE = new Type<SetStateFromPlaceRequestHandler>();

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
