package com.arcbees.gaestudio.client.application.profiler.widget;

import com.gwtplatform.mvp.client.UiHandlers;

public interface StatementUiHandlers extends UiHandlers {
    
    void onStatementClicked(Long statementId);
    
}
