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

import com.arcbees.oauth.client.OAuthClient;
import com.arcbees.oauth.client.UserClient;
import com.arcbees.oauth.client.domain.Token;
import com.arcbees.oauth.client.domain.User;
import com.google.common.base.Strings;

public class SecureAuthService implements AuthService {
    public static final String API_TOKEN = "ljhs98234h24o8dsyfjehrljqh01923874j2hj";

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
    public User register(String email, String password, String firstName, String lastName) {
        Token bearerToken = getBearerToken();

        return userClient.register(bearerToken, email, password, firstName, lastName);
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
        Token token = oAuthClient.login(email, password);

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
        return oAuthClient.getBearerToken(API_TOKEN);
    }
}
