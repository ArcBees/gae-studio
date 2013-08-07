package com.arcbees.gaestudio.client.util;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class JsoMethodCallback<T extends JavaScriptObject> extends MethodCallbackImpl<JavaScriptObject> {
    public JsoMethodCallback() {
    }

    public JsoMethodCallback(String failureMessage) {
        super(failureMessage);
    }

    @Override
    protected final void onSuccess(JavaScriptObject result) {
        onSuccessReceived(result.<T>cast());
    }

    protected abstract void onSuccessReceived(T result);
}
