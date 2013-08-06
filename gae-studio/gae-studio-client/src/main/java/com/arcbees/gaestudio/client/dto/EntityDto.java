package com.arcbees.gaestudio.client.dto;

import com.google.gwt.core.client.JavaScriptObject;

public class EntityDto extends JavaScriptObject {
    protected EntityDto() {
    }

    public final native String getJson() /*-{
        return this.json;
    }-*/;

    public final native KeyDto getKey() /*-{
        return this.key;
    }-*/;
}
