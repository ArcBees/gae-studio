package com.arcbees.gaestudio.client.dto.entity;

import com.google.gwt.core.client.JavaScriptObject;

public class EntityDto extends JavaScriptObject {
    public static native EntityDto create(KeyDto key, String json) /*-{
        return {json: json, key: key};
    }-*/;

    public static EntityDto updateJson(EntityDto entityDto, String json) {
        return create(entityDto.getKey(), json);
    }

    protected EntityDto() {
    }

    public final native String getJson() /*-{
        return this.json;
    }-*/;

    public final native KeyDto getKey() /*-{
        return this.key;
    }-*/;
}
