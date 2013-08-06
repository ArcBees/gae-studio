package com.arcbees.gaestudio.client.dto.entity;

public class KeyDto extends ParentKeyDto {
    public static native KeyDto create(String kind,
                                       Long id,
                                       ParentKeyDto parentKeyDto,
                                       AppIdNamespaceDto appIdNamespaceDto) /*-{
        return {kind: kind, id: id, parentKeyDto: parentKeyDto, appIdNamespaceDto: appIdNamespaceDto};
    }-*/;

    protected KeyDto() {
    }

    public final native ParentKeyDto getParentKey() /*-{
        return this.parentKeyDto;
    }-*/;

    public final native AppIdNamespaceDto getAppIdNamespaceDto() /*-{
        return this.appIdNAmespaceDto;
    }-*/;
}
