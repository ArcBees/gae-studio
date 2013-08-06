package com.arcbees.gaestudio.client.dto.entity;

import com.google.gwt.core.client.JavaScriptObject;

public class AppIdNamespaceDto extends JavaScriptObject {
    public static native AppIdNamespaceDto create(String appId, String namespace) /*-{
        return {appId: appId, namespace: namespace};
    }-*/;

    protected AppIdNamespaceDto() {
    }

    public final native String getAppId() /*-{
        return appId;
    }-*/;

    public final native String getNamespace() /*-{
        return namespace;
    }-*/;
}
