/*
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

    public static void fire(HasHandlers source, FilterValueSelectedEvent eventInstance) {
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
