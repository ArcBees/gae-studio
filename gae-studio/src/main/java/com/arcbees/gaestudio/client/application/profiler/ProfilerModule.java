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
