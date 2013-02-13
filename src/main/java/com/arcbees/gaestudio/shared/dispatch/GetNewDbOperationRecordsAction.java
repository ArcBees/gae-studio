package com.arcbees.gaestudio.shared.dispatch;

import com.gwtplatform.dispatch.shared.Action;

public class GetNewDbOperationRecordsAction implements Action<GetNewDbOperationRecordsResult> {
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

    @Override
    public String getServiceName() {
        return Action.DEFAULT_SERVICE_NAME + "GetNewDbOperationRecords";
    }

    @Override
    public boolean isSecured() {
        return false;
    }

    public Long getLastId() {
        return lastId;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GetNewDbOperationRecordsAction other = (GetNewDbOperationRecordsAction) obj;
        if (lastId == null) {
            if (other.lastId != null)
                return false;
        } else if (!lastId.equals(other.lastId))
            return false;
        if (maxResults == null) {
            if (other.maxResults != null)
                return false;
        } else if (!maxResults.equals(other.maxResults))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (lastId == null ? 1 : lastId.hashCode());
        hashCode = (hashCode * 37) + (maxResults == null ? 1 : maxResults.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "GetNewDbOperationRecordsAction[" + lastId + "," + maxResults + "]";
    }
}
