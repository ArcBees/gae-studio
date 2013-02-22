package com.arcbees.gaestudio.client.util;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AsyncCallbackImpl<T> implements AsyncCallback<T> {
    @Override
    public void onFailure(Throwable caught) {
    }
}
