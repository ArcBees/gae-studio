/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.googleanalytic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

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

    private static final Logger LOG = Logger.getLogger(MeasureProtocolRequest.class.getName());

    private final List<MethodParameterTuple> methodParameterTuples;

    MeasureProtocolRequest(List<MethodParameterTuple> methodParameterTuples) {
        this.methodParameterTuples = methodParameterTuples;
    }

    public boolean executeRequest() {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(GaParameterConstants.POST_URL);

        for (MethodParameterTuple methodParameterTuple : methodParameterTuples) {
            method.addParameter(methodParameterTuple.getName(), methodParameterTuple.getValue());
        }

        try {
            int returnCode = client.executeMethod(method);

            return (returnCode == HttpStatus.SC_OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR: {}", e.getMessage());

            return false;
        }
    }
}
