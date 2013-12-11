/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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

    @Inject
    Provider<VersionPresenter> provider;

    @Test
    public void onBind_anyVersion_setsVersionInView(VersionPresenter.MyView view, AppConfig appConfig) {
        //given
        given(appConfig.getVersion()).willReturn(SOME_VERSION);

        //when
        provider.get();

        //then
        verify(view).setVersion(SOME_VERSION);
    }
}
