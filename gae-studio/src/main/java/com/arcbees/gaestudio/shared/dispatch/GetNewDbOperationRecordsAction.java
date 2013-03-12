/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

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
