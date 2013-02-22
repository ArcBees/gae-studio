package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.gwtplatform.mvp.client.UiHandlers;

public interface TypeFilterUiHandlers extends UiHandlers {
    void onRequestClicked(FilterValue<OperationType> filterValue);
}
