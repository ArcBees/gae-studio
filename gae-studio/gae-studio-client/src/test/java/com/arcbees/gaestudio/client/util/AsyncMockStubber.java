/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.util;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.rest.client.RestCallback;

public class AsyncMockStubber {
    public static <T> Stubber callResponseWith(final Response response) {
        return Mockito.doAnswer(new Answer<T>() {
            @Override
            public T answer(InvocationOnMock invocationOnMock) throws Throwable {
                final Object[] args = invocationOnMock.getArguments();

                ((RestCallback) args[args.length - 1]).setResponse(response);
                return null;
            }
        });
    }

    public static <T> Stubber callSuccessWith(final T response) {
        return Mockito.doAnswer(new Answer<T>() {
            @Override
            public T answer(InvocationOnMock invocationOnMock) throws Throwable {
                final Object[] args = invocationOnMock.getArguments();

                ((AsyncCallback) args[args.length - 1]).onSuccess(response);
                return null;
            }
        });
    }
}
