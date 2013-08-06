package com.arcbees.gaestudio.client.util;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public abstract class MethodCallbackImpl<T> implements MethodCallback<T> {
    @Override
    public void onSuccess(Method method, T result) {
        onSuccess(result);
    }

    @Override
    public void onFailure(Method method, Throwable throwable) {
        onFailure(throwable);
    }

    protected void onFailure(Throwable throwable) {
    }

    protected abstract void onSuccess(T result);
}
