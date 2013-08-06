package com.arcbees.gaestudio.client.dto;

import com.google.gwt.core.client.JavaScriptObject;

public class AppIdNamespaceDto extends JavaScriptObject {
    protected AppIdNamespaceDto() {
    }

    public final native String getAppId() /*-{
        return appId;
    }-*/;

    public final native String getNamespace() /*-{
        return namespace;
    }-*/;
}
