/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppConfig {
    public static class Builder {
        private String restPath;
        private String clientId;

        private Builder() {
        }

        public Builder restPath(String restPath) {
            this.restPath = restPath;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public AppConfig build() {
            return new AppConfig(restPath, clientId);
        }
    }

    public static Builder with() {
        return new Builder();
    }

    public static final String OBJECT_KEY = "AppConfiguration";
    private final String clientId;
    private final String restPath;

    @JsonCreator
    private AppConfig(@JsonProperty("restPath") String restPath,
                      @JsonProperty("clientId") String clientId) {
        this.restPath = restPath;
        this.clientId = clientId;
    }

    public String getRestPath() {
        return restPath;
    }

    public String getClientId() {
        return clientId;
    }
}
