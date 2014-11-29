/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application;

import java.util.logging.Logger;

import javax.inject.Singleton;

import com.arcbees.gaestudio.client.application.analytics.AnalyticsModule;
import com.arcbees.gaestudio.client.application.auth.AuthModule;
import com.arcbees.gaestudio.client.application.channel.ChannelModule;
import com.arcbees.gaestudio.client.application.entity.EntityModule;
import com.arcbees.gaestudio.client.application.license.LicenseModule;
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
        install(new LicenseModule());
        install(new AuthModule());
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
