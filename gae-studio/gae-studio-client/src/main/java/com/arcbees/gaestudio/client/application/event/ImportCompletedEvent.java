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

package com.arcbees.gaestudio.client.application.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ImportCompletedEvent extends GwtEvent<ImportCompletedEvent.ImportCompletedHandler> {
    public interface ImportCompletedHandler extends EventHandler {
        void onImportComplete(ImportCompletedEvent event);
    }

    private static final Type<ImportCompletedHandler> TYPE = new Type<>();

    private ImportCompletedEvent() {
    }

    public static void fire(HasHandlers hasHandlers) {
        hasHandlers.fireEvent(new ImportCompletedEvent());
    }

    public static Type<ImportCompletedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<ImportCompletedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ImportCompletedHandler handler) {
        handler.onImportComplete(this);
    }
}
