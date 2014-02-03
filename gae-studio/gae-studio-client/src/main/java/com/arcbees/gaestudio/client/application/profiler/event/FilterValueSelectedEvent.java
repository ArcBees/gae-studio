/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.event;

import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterValue;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class FilterValueSelectedEvent extends GwtEvent<FilterValueSelectedEvent.FilterValueSelectedHandler> {
    private FilterValue<?> filterValue;

    protected FilterValueSelectedEvent() {
        // Possibly for serialization.
    }

    public FilterValueSelectedEvent(FilterValue<?> filterValue) {
        this.filterValue = filterValue;
    }

    public static void fire(HasHandlers source, FilterValue<?> filterValue) {
        FilterValueSelectedEvent eventInstance = new FilterValueSelectedEvent(filterValue);
        source.fireEvent(eventInstance);
    }

    public interface HasFilterValueSelectedHandlers extends HasHandlers {
        HandlerRegistration addFilterValueSelectedHandler(FilterValueSelectedHandler handler);
    }

    public interface FilterValueSelectedHandler extends EventHandler {
        public void onFilterValueSelected(FilterValueSelectedEvent event);
    }

    private static final Type<FilterValueSelectedHandler> TYPE = new Type<FilterValueSelectedHandler>();

    public static Type<FilterValueSelectedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<FilterValueSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FilterValueSelectedHandler handler) {
        handler.onFilterValueSelected(this);
    }

    public FilterValue<?> getFilterValue() {
        return filterValue;
    }
}
