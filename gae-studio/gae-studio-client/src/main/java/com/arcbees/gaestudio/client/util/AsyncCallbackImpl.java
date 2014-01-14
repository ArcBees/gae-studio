/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.util;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AsyncCallbackImpl<T> implements AsyncCallback<T> {
    private final String failureMessage;

    public AsyncCallbackImpl() {
        failureMessage = null;
    }

    public AsyncCallbackImpl(String failureMessage) {
        this.failureMessage = failureMessage;
    }


    @Override
    public void onFailure(Throwable caught) {
        if (failureMessage != null) {
            Window.alert(failureMessage + ", exception: " + caught.getMessage());
        }
    }
}
