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

public class KindSelectedEvent extends GwtEvent<KindSelectedEvent.KindSelectedHandler> {
    public interface KindSelectedHandler extends EventHandler {
        public void onKindSelected(KindSelectedEvent event);
    }

    public static void fire(HasHandlers source, String kind) {
        source.fireEvent(new KindSelectedEvent(kind));
    }

    public static Type<KindSelectedHandler> getType() {
        return TYPE;
    }

    private static final Type<KindSelectedHandler> TYPE = new Type<>();

    private String kind;

    public KindSelectedEvent(String kind) {
        this.kind = kind;
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
