package com.arcbees.gaestudio.client.dto;

public class KeyDto extends ParentKeyDto {
    protected KeyDto() {
    }

    public final native ParentKeyDto getParentKeyDto() /*-{
        return this.parentKeyDto;
    }-*/;

    public final native AppIdNamespaceDto getAppIdNamespaceDto() /*-{
        return this.appIdNAmespaceDto;
    }-*/;
}
