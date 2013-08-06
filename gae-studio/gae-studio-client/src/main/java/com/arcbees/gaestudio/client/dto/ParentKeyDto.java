package com.arcbees.gaestudio.client.dto;

import com.google.gwt.core.client.JavaScriptObject;

public class ParentKeyDto extends JavaScriptObject {
    protected ParentKeyDto() {
    }

    public final native String getKind() /*-{
        return this.kind;
    }-*/;

    public final native Long getId() /*-{
        return this.id;
    }-*/;
}
