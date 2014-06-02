/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.auth;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

import com.arcbees.oauth.api.OAuthClient;
import com.arcbees.oauth.api.UserClient;
import com.arcbees.oauth.api.domain.Token;
import com.arcbees.oauth.api.domain.User;
import com.google.common.base.Strings;

public class SecureAuthService implements AuthService {
    private static final String CONSUMER_KEY = "00a2ba2d-d30c-4630-9ced-5d11b4ba5330";
    private static final String TOKEN = "token";

    private final OAuthClient oAuthClient;
    private final UserClient userClient;
    private final Provider<HttpSession> sessionProvider;

    @Inject
    SecureAuthService(OAuthClient oAuthClient,
                      UserClient userClient,
                      Provider<HttpSession> sessionProvider) {
        this.sessionProvider = sessionProvider;
        this.oAuthClient = oAuthClient;
        this.userClient = userClient;
    }

    @Override
    public String register(String email, String password, String firstName, String lastName) {
        Token bearerToken = getBearerToken();

        Long userId = userClient.register(bearerToken, email, password, firstName, lastName);
        userClient.addUserPermission(bearerToken, userId);

        return userClient.getRegistrationToken(userId);
    }

    @Override
    public void requestResetToken(String email) {
        Token bearerToken = getBearerToken();

        userClient.requestResetPassword(bearerToken, email);
    }

    @Override
    public User checkLogin() {
        String authToken = getSavedAuthToken();

        User user = null;
        if (!Strings.isNullOrEmpty(authToken)) {
            user = oAuthClient.checkLogin(authToken);
        }

        return user;
    }

    @Override
    public Token login(String email, String password) {
        Token bearerToken = oAuthClient.getBearerToken(CONSUMER_KEY);
        Token token = oAuthClient.login(bearerToken.getToken(), email, password);

        saveAuthToken(token);

        return token;
    }

    @Override
    public void logout() {
        String authToken = getSavedAuthToken();

        oAuthClient.logout(authToken);
    }

    private String getSavedAuthToken() {
        return (String) sessionProvider.get().getAttribute(TOKEN);
    }

    private void saveAuthToken(Token token) {
        sessionProvider.get().setAttribute(TOKEN, token.getToken());
    }

    private Token getBearerToken() {
        return oAuthClient.getBearerToken(CONSUMER_KEY);
    }
}
