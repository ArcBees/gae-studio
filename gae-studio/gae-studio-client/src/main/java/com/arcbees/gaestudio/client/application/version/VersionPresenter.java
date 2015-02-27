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
    VersionPresenter(
            EventBus eventBus,
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
