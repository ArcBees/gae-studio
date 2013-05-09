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

public class GetEntitiesByKindAction extends GaeStudioActionImpl<GetEntitiesByKindResult> {
    private String kind;
    private Integer offset;
    private Integer limit;

    public static class Builder {
        // Required parameters
        private final String kind;

        // Optional parameters - initialized to default values
        private Integer offset;
        private Integer limit;

        public Builder(String kind) {
            this.kind = kind;
        }

        public Builder offset(Integer offset) {
            this.offset = offset;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public GetEntitiesByKindAction build() {
            return new GetEntitiesByKindAction(this);
        }
    }

    protected GetEntitiesByKindAction() {
        // Possibly for serialization.
    }

    public GetEntitiesByKindAction(String kind) {
        this.kind = kind;
    }

    private GetEntitiesByKindAction(Builder builder) {
        this.kind = builder.kind;
        this.offset = builder.offset;
        this.limit = builder.limit;
    }

    public String getKind() {
        return kind;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getLimit() {
        return limit;
    }
}
