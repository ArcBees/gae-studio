/**
 * Copyright 2015 ArcBees Inc.
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

package com.arcbees.gaestudio.shared.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppConfig {
    public static class Builder {
        private String restPath;
        private String version;
        private String latestVersion;
        private boolean useCookieDomainNone;

        private Builder() {
        }

        public Builder restPath(String restPath) {
            this.restPath = restPath;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder latestVersion(String latestVersion) {
            this.latestVersion = latestVersion;
            return this;
        }

        public Builder useCookieDomainNone(boolean useCookieDomainNone) {
            this.useCookieDomainNone = useCookieDomainNone;
            return this;
        }

        public AppConfig build() {
            return new AppConfig(restPath, version, latestVersion, useCookieDomainNone);
        }
    }

    public static final String OBJECT_KEY = "AppConfiguration";

    private final String restPath;
    private final String version;
    private final String latestVersion;
    private final boolean useCookieDomainNone;

    @JsonCreator
    private AppConfig(
            @JsonProperty("restPath") String restPath,
            @JsonProperty("version") String version,
            @JsonProperty("latestVersion") String latestVersion,
            @JsonProperty("useCookieDomainNone") boolean useCookieDomainNone) {
        this.restPath = restPath;
        this.version = version;
        this.latestVersion = latestVersion;
        this.useCookieDomainNone = useCookieDomainNone;
    }

    public static Builder with() {
        return new Builder();
    }

    public String getVersion() {
        return version;
    }

    public String getRestPath() {
        return restPath;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public boolean isUseCookieDomainNone() {
        return useCookieDomainNone;
    }
}
