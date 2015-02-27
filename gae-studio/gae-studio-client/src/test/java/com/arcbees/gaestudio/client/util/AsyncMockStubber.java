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
