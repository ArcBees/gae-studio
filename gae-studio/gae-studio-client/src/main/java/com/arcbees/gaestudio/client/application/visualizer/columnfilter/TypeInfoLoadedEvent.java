/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.columnfilter;

import java.util.List;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class TypeInfoLoadedEvent extends GwtEvent<TypeInfoLoadedEvent.TypeInfoLoadedHandler> {
    public interface TypeInfoLoadedHandler extends EventHandler {
        void onTypeInfoLoaded(TypeInfoLoadedEvent event);
    }

    private final List<String> columnNames;
    private final ParsedEntity prototype;

    public TypeInfoLoadedEvent(List<String> columnNames, ParsedEntity prototype) {
        this.columnNames = columnNames;
        this.prototype = prototype;
    }

    public static void fire(HasHandlers source, List<String> columnNames, ParsedEntity prototype) {
        TypeInfoLoadedEvent eventInstance = new TypeInfoLoadedEvent(columnNames, prototype);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, TypeInfoLoadedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    private static final Type<TypeInfoLoadedHandler> TYPE = new Type<>();

    public static Type<TypeInfoLoadedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<TypeInfoLoadedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(TypeInfoLoadedHandler handler) {
        handler.onTypeInfoLoaded(this);
    }

    public ParsedEntity getPrototype() {
        return prototype;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }
}
