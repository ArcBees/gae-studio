package com.arcbees.gaestudio.client.application.profiler;

import com.gwtplatform.mvp.client.UiHandlers;

public interface RequestUiHandlers extends UiHandlers {

    void onRequestClicked(Long requestId);

}
