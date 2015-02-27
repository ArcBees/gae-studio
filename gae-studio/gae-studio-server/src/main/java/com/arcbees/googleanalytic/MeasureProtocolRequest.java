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

package com.arcbees.googleanalytic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public class MeasureProtocolRequest {
    public static class Builder {
        private final List<MethodParameterTuple> parameterTuples = new ArrayList<MethodParameterTuple>();

        public Builder() {
        }

        public Builder protocolVersion(String protocolVersion) {
            parameterTuples.add(new MethodParameterTuple(GaParameterConstants.PROTOCOL_VERSION, protocolVersion));

            return this;
        }

        public Builder clientId(String clientId) {
            parameterTuples.add(new MethodParameterTuple(GaParameterConstants.CLIENT_ID, clientId));

            return this;
        }

        public Builder trackingCode(String trackId) {
            parameterTuples.add(new MethodParameterTuple(GaParameterConstants.TRACKING_ID, trackId));

            return this;
        }

        public Builder hitType(String hitType) {
            parameterTuples.add(new MethodParameterTuple(GaParameterConstants.HIT_TYPE, hitType));

            return this;
        }

        public Builder applicationName(String applicationName) {
            parameterTuples.add(new MethodParameterTuple(GaParameterConstants.APPLICATION_NAME, applicationName));

            return this;
        }

        public Builder applicationVersion(String applicationVersion) {
            parameterTuples.add(new MethodParameterTuple(GaParameterConstants.APPLICATION_VERSION, applicationVersion));

            return this;
        }

        public Builder eventCategory(String category) {
            parameterTuples.add(new MethodParameterTuple(GaParameterConstants.EVENT_CATEGORY, category));

            return this;
        }

        public Builder eventAction(String eventAction) {
            parameterTuples.add(new MethodParameterTuple(GaParameterConstants.EVENT_ACTION, eventAction));

            return this;
        }

        public Builder eventLabel(String eventLabel) {
            parameterTuples.add(new MethodParameterTuple(GaParameterConstants.EVENT_LABEL, eventLabel));

            return this;
        }

        public Builder eventValue(String eventValue) {
            parameterTuples.add(new MethodParameterTuple(GaParameterConstants.EVENT_VALUE, eventValue));

            return this;
        }

        public MeasureProtocolRequest build() {
            return new MeasureProtocolRequest(parameterTuples);
        }
    }

    private final List<MethodParameterTuple> methodParameterTuples;

    MeasureProtocolRequest(List<MethodParameterTuple> methodParameterTuples) {
        this.methodParameterTuples = methodParameterTuples;
    }

    public boolean executeRequest() {
        HttpTransport httpTransport = new NetHttpTransport();
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory();

        GenericUrl genericUrl = new GenericUrl(GaParameterConstants.POST_URL);

        for (MethodParameterTuple methodParameterTuple : methodParameterTuples) {
            genericUrl.put(methodParameterTuple.getName(), methodParameterTuple.getValue());
        }

        try {
            HttpRequest request = requestFactory.buildGetRequest(genericUrl);
            HttpResponse response = request.execute();

            return response.getStatusCode() == HttpStatusCodes.STATUS_CODE_OK;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
