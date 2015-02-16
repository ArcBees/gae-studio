/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class PropertyEditorErrorEvent extends GwtEvent<PropertyEditorErrorEvent.PropertyEditorErrorHandler> {
    public interface PropertyEditorErrorHandler extends EventHandler {
        public void onPropertyEditorError(PropertyEditorErrorEvent event);
    }

    public static Type<PropertyEditorErrorHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, String error) {
        PropertyEditorErrorEvent eventInstance = new PropertyEditorErrorEvent(error);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, PropertyEditorErrorEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    private static final Type<PropertyEditorErrorHandler> TYPE = new Type<PropertyEditorErrorHandler>();

    private String error;

    PropertyEditorErrorEvent(String error) {
        this.error = error;
    }

    @Override
    public Type<PropertyEditorErrorHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(PropertyEditorErrorHandler handler) {
        handler.onPropertyEditorError(this);
    }

    public String getError() {
        return error;
    }
}
