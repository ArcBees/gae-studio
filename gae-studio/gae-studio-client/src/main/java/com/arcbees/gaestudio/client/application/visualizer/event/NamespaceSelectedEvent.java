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

public class NamespaceSelectedEvent extends GwtEvent<NamespaceSelectedEvent.NamespaceSelectedHandler> {
    public interface NamespaceSelectedHandler extends EventHandler {
        public void onNamespaceSelected(NamespaceSelectedEvent event);
    }

    public static void fire(HasHandlers source, String namespace) {
        source.fireEvent(new NamespaceSelectedEvent(namespace));
    }

    public static Type<NamespaceSelectedHandler> getType() {
        return TYPE;
    }

    private static final Type<NamespaceSelectedHandler> TYPE = new Type<>();

    private String namespace;

    public NamespaceSelectedEvent(String namespace) {
        this.namespace = namespace;
    }

    public String getNamespace() {
        return namespace;
    }

    @Override
    public Type<NamespaceSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(NamespaceSelectedHandler handler) {
        handler.onNamespaceSelected(this);
    }
}
