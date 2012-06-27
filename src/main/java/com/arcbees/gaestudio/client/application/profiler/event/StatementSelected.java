package com.arcbees.gaestudio.client.application.profiler.event;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDTO;
import com.gwtplatform.dispatch.annotation.GenEvent;

@GenEvent
public class StatementSelected {

    DbOperationRecordDTO record;

}
