package com.arcbees.gaestudio.client.dto.query;

import com.google.gwt.core.client.JavaScriptObject;

public class QueryResultDto extends JavaScriptObject {
    protected QueryResultDto() {
    }

    public final native Integer getResultSize() /*-{
        return this.resultSize;
    }-*/;

    public final native Integer getSerializedSize() /*-{
        return this.serializedSize;
    }-*/;
}
