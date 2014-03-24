/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.version;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.config.AppConfig;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class VersionPresenter extends PresenterWidget<VersionPresenter.MyView> {
    public interface MyView extends View {
        void setVersion(String version, String latestVersion);
    }

    private final AppConfig appConfig;

    @Inject
    VersionPresenter(EventBus eventBus,
                     MyView view,
                     AppConfig appConfig) {
        super(eventBus, view);

        this.appConfig = appConfig;
    }

    @Override
    protected void onBind() {
        super.onBind();

        getView().setVersion(appConfig.getVersion(), appConfig.getLatestVersion());
    }
}
