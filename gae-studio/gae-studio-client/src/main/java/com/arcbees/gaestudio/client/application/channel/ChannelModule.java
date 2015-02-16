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

package com.arcbees.gaestudio.client.application.channel;

import javax.inject.Singleton;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.multibindings.GinMultibinder;

public class ChannelModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(ChannelHandler.class).in(Singleton.class);

        GinMultibinder<MessageHandler> multibinder = GinMultibinder.newSetBinder(binder(), MessageHandler.class);
        multibinder.addBinding().to(ImportCompletedMessageHandler.class);
        multibinder.addBinding().to(ImportTooLargeMessageHandler.class);
        multibinder.addBinding().to(ExportCompletedMessageHandler.class);
        multibinder.addBinding().to(DbOperationMessageHandler.class);
    }
}
