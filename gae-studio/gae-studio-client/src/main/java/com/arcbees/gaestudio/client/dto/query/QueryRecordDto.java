package com.arcbees.gaestudio.client.dto.query;

import com.arcbees.gaestudio.client.dto.DbOperationRecordDto;

public class QueryRecordDto extends DbOperationRecordDto {
    protected QueryRecordDto() {
    }

    public final native QueryDto getQuery() /*-{
        return this.query;
    }-*/;

    public final native QueryResultDto getQueryResult() /*-{
        return this.queryResult;
    }-*/;
}
