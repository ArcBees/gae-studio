package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.gwtplatform.mvp.client.UiHandlers;

public interface StatementUiHandlers extends UiHandlers {

    void onStatementClicked(DbOperationRecordDto record);

}
