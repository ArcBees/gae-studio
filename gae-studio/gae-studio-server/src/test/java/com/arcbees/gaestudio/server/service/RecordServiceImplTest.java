/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service;

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.recorder.HookRegistrar;
import com.arcbees.gaestudio.server.recorder.authentication.Listener;
import com.arcbees.gaestudio.server.recorder.authentication.ListenerProvider;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class RecordServiceImplTest {
    public static class RecordServiceModule extends JukitoModule {
        @Override
        protected void configureTest() {
            forceMock(HookRegistrar.class);
            forceMock(ListenerProvider.class);
            forceMock(Listener.class);
        }
    }
    @Inject
    RecordServiceImpl recordService;
    @Inject
    HookRegistrar hookRegistrar;
    @Inject
    ListenerProvider listenerProvider;

    @Test
    public void testStartRecording(Listener listener) {
        //GIVEN
        when(listenerProvider.get()).thenReturn(listener);
        //WHEN
        recordService.startRecording();

        //THEN
        verify(listenerProvider).get();
        verify(hookRegistrar).putListener(listener);
    }

    @Test
    public void testStopRecording(Listener listener) {
        //GIVEN
        when(listenerProvider.get()).thenReturn(listener);
        //WHEN
        recordService.stopRecording();

        //THEN
        verify(listenerProvider).get();
        verify(hookRegistrar).removeListener(listener);
    }

}
