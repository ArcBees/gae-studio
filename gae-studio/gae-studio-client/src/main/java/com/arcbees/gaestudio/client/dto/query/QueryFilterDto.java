package com.arcbees.gaestudio.client.dto.query;

import com.arcbees.gaestudio.shared.QueryFilterOperator;
import com.google.common.base.Strings;
import com.google.gwt.core.client.JavaScriptObject;

public class QueryFilterDto extends JavaScriptObject {
    protected QueryFilterDto() {
    }

    public final native String getProperty() /*-{
        return this.property;
    }-*/;

    public final native String getValue() /*-{
        return this.value;
    }-*/;

    public final QueryFilterOperator getOperator() {
        String operatorString = getOperatorString();

        QueryFilterOperator operator = null;

        if (!Strings.isNullOrEmpty(operatorString)) {
            operator = QueryFilterOperator.valueOf(operatorString);
        }

        return operator;
    }

    private native String getOperatorString() /*-{
        return this.operator;
    }-*/;
}
