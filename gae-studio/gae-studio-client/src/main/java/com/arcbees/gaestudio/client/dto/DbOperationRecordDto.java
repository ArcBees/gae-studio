package com.arcbees.gaestudio.client.dto;

import com.arcbees.gaestudio.client.dto.stacktrace.StackTraceElementDto;
import com.google.gwt.core.client.JavaScriptObject;

public class DbOperationRecordDto extends JavaScriptObject {
    protected DbOperationRecordDto() {
    }

    public final native Long getRequestId() /*-{
        return this.requestId;
    }-*/;

    public final native Long getStatementId() /*-{
        return this.statementId;
    }-*/;

    public final native Integer getExecutionTimeMs() /*-{
        return this.executionTimeMs;
    }-*/;

    public final native StackTraceElementDto getCallerStackTraceElement() /*-{
        return this.callerStackTraceElement;
    }-*/;
}
