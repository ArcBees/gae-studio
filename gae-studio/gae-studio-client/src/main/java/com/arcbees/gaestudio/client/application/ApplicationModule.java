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

package com.arcbees.gaestudio.client.application;

import java.util.logging.Logger;

import javax.inject.Singleton;

import com.arcbees.gaestudio.client.application.analytics.AnalyticsModule;
import com.arcbees.gaestudio.client.application.channel.ChannelModule;
import com.arcbees.gaestudio.client.application.entity.EntityModule;
import com.arcbees.gaestudio.client.application.profiler.ProfilerModule;
import com.arcbees.gaestudio.client.application.support.SupportModule;
import com.arcbees.gaestudio.client.application.ui.UiFactory;
import com.arcbees.gaestudio.client.application.visualizer.VisualizerModule;
import com.arcbees.gaestudio.client.application.widget.HeaderModule;
import com.arcbees.gaestudio.client.application.widget.WidgetModule;
import com.arcbees.gaestudio.client.application.widget.message.MessagesModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Provides;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new WidgetModule());
        install(new HeaderModule());
        install(new ProfilerModule());
        install(new VisualizerModule());
        install(new MessagesModule());
        install(new EntityModule());
        install(new AnalyticsModule());
        install(new SupportModule());
        install(new ChannelModule());
        install(new GinFactoryModuleBuilder().build(UiFactory.class));

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }

    @Provides
    @Singleton
    Logger logger() {
        return Logger.getLogger("debug logger");
    }
}
