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

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class DeleteEntityEvent extends GwtEvent<DeleteEntityEvent.DeleteEntityHandler> {
    public interface DeleteEntityHandler extends EventHandler {
        void onDeleteEntity(DeleteEntityEvent event);
    }

    private static final Type<DeleteEntityHandler> TYPE = new Type<DeleteEntityHandler>();

    private ParsedEntity parsedEntity;

    public DeleteEntityEvent(ParsedEntity parsedEntity) {
        this.parsedEntity = parsedEntity;
    }

    public static Type<DeleteEntityHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, ParsedEntity parsedEntity) {
        DeleteEntityEvent eventInstance = new DeleteEntityEvent(parsedEntity);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, DeleteEntityEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    @Override
    protected void dispatch(DeleteEntityHandler handler) {
        handler.onDeleteEntity(this);
    }

    @Override
    public Type<DeleteEntityHandler> getAssociatedType() {
        return TYPE;
    }

    public ParsedEntity getParsedEntity() {
        return parsedEntity;
    }
}
