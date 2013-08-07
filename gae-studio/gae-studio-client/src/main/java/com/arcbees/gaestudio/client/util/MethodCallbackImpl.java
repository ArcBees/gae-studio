package com.arcbees.gaestudio.client.util;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.user.client.Window;

public abstract class MethodCallbackImpl<T> implements MethodCallback<T> {
    private final String failureMessage;

    public MethodCallbackImpl() {
        failureMessage = null;
    }

    public MethodCallbackImpl(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    @Override
    public void onSuccess(Method method, T result) {
        onSuccess(result);
    }

    @Override
    public void onFailure(Method method, Throwable caught) {
        onFailure(caught);

        if (failureMessage != null) {
            Window.alert(failureMessage + ", exception: " + caught.getMessage());
        }
    }

    protected void onFailure(Throwable caught) {
    }

    protected abstract void onSuccess(T result);
}
