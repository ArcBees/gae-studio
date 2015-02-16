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

package com.arcbees.gaestudio.server.recorder.authentication;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

public class ListenerProvider implements Provider<Listener> {
    public static final String LISTENER_ID_COOKIE = "LISTENER_ID";
    private final Provider<HttpSession> sessionProvider;

    @Inject
    ListenerProvider(
            Provider<HttpSession> sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    @Override
    public Listener get() {
        HttpSession session = sessionProvider.get();
        String listenerId = (String) session.getAttribute(LISTENER_ID_COOKIE);

        if (listenerId == null) {
            listenerId = UUID.randomUUID().toString();
            session.setAttribute(LISTENER_ID_COOKIE, listenerId);
        }
        return new Listener(listenerId);
    }
}
