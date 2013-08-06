package com.arcbees.gaestudio.client.dto.query;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;

public class QueryDto extends JavaScriptObject {
    protected QueryDto() {
    }

    public final native String getKind() /*-{
        return this.kind;
    }-*/;

    public final native String getAncestor() /*-{
        return this.ancestor;
    }-*/;

    public final native List<QueryFilterDto> getFilters() /*-{
        return this.filters;
    }-*/;

    public final native List<QueryOrderDto> getOrders() /*-{
        return this.orders;
    }-*/;

    public final native Integer getOffset() /*-{
        return this.offset;
    }-*/;

    public final native Integer getLimit() /*-{
        return this.limit;
    }-*/;
}
