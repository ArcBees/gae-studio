/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.server.recorder.authentication;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class ListenerProvider implements Provider<Listener> {
    public static final String LISTENER_ID_COOKIE = "LISTENER_ID";
    private final Provider<HttpSession> sessionProvider;

    @Inject
    public ListenerProvider(final Provider<HttpSession> sessionProvider) {
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
