/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler;

import javax.inject.Singleton;

import com.arcbees.gaestudio.client.application.profiler.widget.ProfilerWidgetModule;
import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterModule;
import com.arcbees.gaestudio.client.formatters.ObjectifyRecordFormatter;
import com.arcbees.gaestudio.client.formatters.RecordFormatter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ProfilerModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new FilterModule());
        install(new ProfilerWidgetModule());

        bind(RecordFormatter.class).to(ObjectifyRecordFormatter.class).in(Singleton.class);

        bindPresenter(ProfilerPresenter.class, ProfilerPresenter.MyView.class,
                ProfilerView.class, ProfilerPresenter.MyProxy.class);
    }
}
