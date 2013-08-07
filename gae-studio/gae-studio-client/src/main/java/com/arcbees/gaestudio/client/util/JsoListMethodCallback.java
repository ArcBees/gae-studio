package com.arcbees.gaestudio.client.util;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.JavaScriptObject;

public abstract class JsoListMethodCallback<T extends JavaScriptObject>
        extends MethodCallbackImpl<List<JavaScriptObject>> {
    @Override
    protected final void onSuccess(List<JavaScriptObject> result) {
        List<T> listResult = Lists.newArrayList();

        for (JavaScriptObject element : result) {
            listResult.add(element.<T>cast());
        }

        onSuccessReceived(listResult);
    }

    protected abstract void onSuccessReceived(List<T> result);
}
