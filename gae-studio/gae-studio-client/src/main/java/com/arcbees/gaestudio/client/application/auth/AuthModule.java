/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import com.arcbees.analytics.client.universalanalytics.UniversalAnalyticsModule;
import com.arcbees.gaestudio.client.application.auth.activation.ActivationModule;
import com.arcbees.gaestudio.client.application.auth.forgot.ForgotPasswordModule;
import com.arcbees.gaestudio.client.application.auth.register.RegisterModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class AuthModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new ForgotPasswordModule());
        install(new RegisterModule());
        install(new ActivationModule());

        install(new UniversalAnalyticsModule.Builder("UA-41550930-10").autoCreate(false).build());

        bindPresenter(AuthPresenter.class, AuthPresenter.MyView.class, AuthView.class,
                AuthPresenter.MyProxy.class);

        bind(LoginHelper.class).to(LoginHelperImpl.class);
    }
}
