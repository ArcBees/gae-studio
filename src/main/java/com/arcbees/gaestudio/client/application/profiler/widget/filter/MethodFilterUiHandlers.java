package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.gwtplatform.mvp.client.UiHandlers;

public interface MethodFilterUiHandlers extends UiHandlers {

    void onMethodClicked(String className, String methodName);

}
