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

package com.arcbees.gaestudio.server.channel;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

import com.arcbees.gaestudio.shared.channel.Constants;

public class ClientIdProvider implements Provider<String> {
    private final Provider<HttpSession> httpSessionProvider;

    @Inject
    ClientIdProvider(
            Provider<HttpSession> httpSessionProvider) {
        this.httpSessionProvider = httpSessionProvider;
    }

    @Override
    public String get() {
        HttpSession httpSession = httpSessionProvider.get();

        Object clientId = httpSession.getAttribute(Constants.CLIENT_ID);

        if (clientId == null) {
            clientId = UUID.randomUUID();
            httpSession.setAttribute(Constants.CLIENT_ID, clientId);
        }

        return clientId.toString();
    }
}
