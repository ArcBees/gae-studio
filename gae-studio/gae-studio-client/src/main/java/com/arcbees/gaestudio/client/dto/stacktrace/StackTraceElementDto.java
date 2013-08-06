package com.arcbees.gaestudio.client.dto.stacktrace;

import com.google.gwt.core.client.JavaScriptObject;

public class StackTraceElementDto extends JavaScriptObject {
    protected StackTraceElementDto() {
    }

    public final native String getClassName() /*-{
        return this.className;
    }-*/;

    public final native String getFileName() /*-{
        return this.fileName;
    }-*/;

    public final native int getLineNumber() /*-{
        return this.lineNumber;
    }-*/;

    public final native String getMethodName() /*-{
        return this.methodName;
    }-*/;
}
