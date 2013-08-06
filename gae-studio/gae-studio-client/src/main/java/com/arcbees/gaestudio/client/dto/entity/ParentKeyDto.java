package com.arcbees.gaestudio.client.dto.entity;

import com.google.gwt.core.client.JavaScriptObject;

public class ParentKeyDto extends JavaScriptObject {
    public static native ParentKeyDto create(String kind, Long id) /*-{
        return {kind: kind, id: id};
    }-*/;

    protected ParentKeyDto() {
    }

    public final native String getKind() /*-{
        return this.kind;
    }-*/;

    public final native Long getId() /*-{
        return this.id;
    }-*/;
}
