package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.gwtplatform.mvp.client.UiHandlers;

public interface RequestFilterUiHandlers extends UiHandlers {
    void onRequestClicked(FilterValue<Long> filterValue);
}
