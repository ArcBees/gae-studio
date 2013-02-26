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

package com.arcbees.gaestudio.client.application.visualizer.event;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class EditEntityEvent extends GwtEvent<EditEntityEvent.EditEntityHandler> {
    private ParsedEntity parsedEntity;

    protected EditEntityEvent() {
        // Possibly for serialization.
    }

    public EditEntityEvent(ParsedEntity parsedEntity) {
        this.parsedEntity = parsedEntity;
    }

    public static void fire(HasHandlers source, ParsedEntity parsedEntity) {
        EditEntityEvent eventInstance = new EditEntityEvent(parsedEntity);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, EditEntityEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasEditEntityHandlers extends HasHandlers {
        HandlerRegistration addEditEntityHandler(EditEntityHandler handler);
    }

    public interface EditEntityHandler extends EventHandler {
        public void onEditEntity(EditEntityEvent event);
    }

    private static final Type<EditEntityHandler> TYPE = new Type<EditEntityHandler>();

    public static Type<EditEntityHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<EditEntityHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EditEntityHandler handler) {
        handler.onEditEntity(this);
    }

    public ParsedEntity getParsedEntity() {
        return parsedEntity;
    }
}
