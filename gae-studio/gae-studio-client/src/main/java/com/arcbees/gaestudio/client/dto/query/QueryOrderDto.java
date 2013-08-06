package com.arcbees.gaestudio.client.dto.query;

import com.arcbees.gaestudio.shared.QueryOrderDirection;
import com.google.common.base.Strings;
import com.google.gwt.core.client.JavaScriptObject;

public class QueryOrderDto extends JavaScriptObject {
    protected QueryOrderDto() {
    }

    public final native String getProperty() /*-{
        return this.property;
    }-*/;

    public final QueryOrderDirection getDirection() {
        String directionString = getDirectionString();

        QueryOrderDirection direction = null;

        if (!Strings.isNullOrEmpty(directionString)) {
            direction = QueryOrderDirection.valueOf(directionString);
        }

        return direction;
    }

    private native String getDirectionString() /*-{
        return this.direction;
    }-*/;
}
