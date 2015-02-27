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
import javax.inject.Provider;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.shared.config.AppConfig;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class VersionPresenterTest {
    private static final String SOME_VERSION = "some version";
    private static final String LATEST_VERSION = "latest version";

    @Inject
    Provider<VersionPresenter> provider;

    @Test
    public void onBind_anyVersion_setsVersionInView(VersionPresenter.MyView view, AppConfig appConfig) {
        // given
        given(appConfig.getVersion()).willReturn(SOME_VERSION);
        given(appConfig.getLatestVersion()).willReturn(LATEST_VERSION);

        // when
        provider.get();

        // then
        verify(view).setVersion(SOME_VERSION, LATEST_VERSION);
    }
}
