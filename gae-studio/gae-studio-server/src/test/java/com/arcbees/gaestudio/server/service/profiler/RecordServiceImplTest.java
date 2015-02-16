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

package com.arcbees.gaestudio.server.service.profiler;

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
        // GIVEN
        when(listenerProvider.get()).thenReturn(listener);

        // WHEN
        recordService.startRecording();

        // THEN
        verify(listenerProvider).get();
        verify(hookRegistrar).putListener(listener);
    }

    @Test
    public void testStopRecording(Listener listener) {
        // GIVEN
        when(listenerProvider.get()).thenReturn(listener);

        // WHEN
        recordService.stopRecording();

        // THEN
        verify(listenerProvider).get();
        verify(hookRegistrar).removeListener(listener);
    }
}
