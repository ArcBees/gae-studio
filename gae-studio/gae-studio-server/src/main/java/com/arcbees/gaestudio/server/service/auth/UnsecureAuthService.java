/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.service.auth;

import com.arcbees.oauth.client.domain.Token;
import com.arcbees.oauth.client.domain.User;

public class UnsecureAuthService implements AuthService{
    @Override
    public User register(String email, String password, String firstName, String lastName) {
        return null;
    }

    @Override
    public void requestResetToken(String email) {
    }

    @Override
    public User checkLogin() {
        return new User();
    }

    @Override
    public Token login(String email, String password) {
        return null;
    }
}
