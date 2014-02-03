/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
    ListenerProvider(Provider<HttpSession> sessionProvider) {
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
