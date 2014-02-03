/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application;

import javax.inject.Inject;
import javax.inject.Provider;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.client.application.version.VersionPresenter;

import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class ApplicationPresenterTest {
    @Inject
    Provider<ApplicationPresenter> provider;

    @Test
    public void onBind_anytime_setsVersionInSlot(ApplicationPresenter.MyView view, VersionPresenter versionPresenter) {
        //when
        provider.get();

        //then
        verify(view).setInSlot(ApplicationPresenter.SLOT_VERSION, versionPresenter);
    }
}
