/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.auth;

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import com.arcbees.gaestudio.client.util.AsyncMockStubber;
import com.arcbees.gaestudio.shared.auth.Token;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.rest.shared.RestAction;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;

import static org.mockito.Mockito.verify;

@RunWith(JukitoRunner.class)
public class AuthPresenterTest {
    public static class MyModule extends JukitoModule {
        @Override
        protected void configureTest() {
            forceMock(Token.class);
        }
    }

    @Inject
    AuthPresenter presenter;

    private final String ANY_STRING = "";

    @Test
    public void login_success_callsLoginHelper(LoginHelper loginHelper,
                                               RestDispatch dispatch,
                                               Token token) {
        //given
        AsyncMockStubber.callSuccessWith(token).when(dispatch).execute(Matchers.<RestAction>any(),
                Matchers.<AsyncCallback>any());

        //when
        presenter.login(ANY_STRING, ANY_STRING);

        //then
        verify(loginHelper).login();
    }
}
