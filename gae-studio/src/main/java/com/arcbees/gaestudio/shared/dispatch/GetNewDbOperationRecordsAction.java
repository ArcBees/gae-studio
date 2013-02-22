package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dispatch.util.GaeStudioActionImpl;

public class GetNewDbOperationRecordsAction extends GaeStudioActionImpl<GetNewDbOperationRecordsResult> {
    private Long lastId;
    private Integer maxResults;

    public static class Builder {
        // Required parameters
        private final Long lastId;

        // Optional parameters - initialized to default values
        private Integer maxResults;

        public Builder(Long lastId) {
            this.lastId = lastId;
        }

        public Builder maxResults(Integer maxResults) {
            this.maxResults = maxResults;
            return this;
        }

        public GetNewDbOperationRecordsAction build() {
            return new GetNewDbOperationRecordsAction(this);
        }
    }

    protected GetNewDbOperationRecordsAction() {
        // Possibly for serialization.
    }

    public GetNewDbOperationRecordsAction(Long lastId) {
        this.lastId = lastId;
    }

    private GetNewDbOperationRecordsAction(Builder builder) {
        this.lastId = builder.lastId;
        this.maxResults = builder.maxResults;
    }

    public Long getLastId() {
        return lastId;
    }

    public Integer getMaxResults() {
        return maxResults;
    }
}
