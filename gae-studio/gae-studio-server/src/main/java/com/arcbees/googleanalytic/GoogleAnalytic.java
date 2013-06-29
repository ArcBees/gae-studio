/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.googleanalytic;

import java.util.UUID;

public class GoogleAnalytic {
    private static final String CLIENT_ID = UUID.randomUUID().toString();
    private static final String PROTOCOL_VERSION = "1";

    private final String appName;
    private final String appVersion;
    private final String trackingCode;

    private GoogleAnalytic(String appName,
                           String trackingCode) {
        this(appName, GaParameterConstants.DEFAULT_APP_VERSION, trackingCode);
    }

    private GoogleAnalytic(String appName,
                           String appVersion,
                           String trackingCode) {
        this.appName = appName;
        this.appVersion = appVersion;
        this.trackingCode = trackingCode;
    }

    public boolean sendEvent(String eventLabel) {
        MeasureProtocolRequest measureProtocolRequest = new MeasureProtocolRequest.Builder()
                .clientId(CLIENT_ID)
                .protocolVersion(PROTOCOL_VERSION)
                .applicationName(appName)
                .applicationVersion(appVersion)
                .trackingCode(trackingCode)
                .hitType(GaParameterConstants.EVENT_HIT_TYPE)
                .eventCategory(GaParameterConstants.EVENT_DEFAULT_CATEGORY)
                .eventAction(GaParameterConstants.EVENT_DEFAULT_ACTION)
                .eventLabel(eventLabel)
                .build();

        return measureProtocolRequest.executeRequest();
    }

    public boolean sendEvent(String eventLabel,
                             Integer eventValue) {
        MeasureProtocolRequest measureProtocolRequest = new MeasureProtocolRequest.Builder()
                .clientId(CLIENT_ID)
                .protocolVersion(PROTOCOL_VERSION)
                .applicationName(appName)
                .applicationVersion(appVersion)
                .trackingCode(trackingCode)
                .hitType(GaParameterConstants.EVENT_HIT_TYPE)
                .eventCategory(GaParameterConstants.EVENT_DEFAULT_CATEGORY)
                .eventAction(GaParameterConstants.EVENT_DEFAULT_ACTION)
                .eventLabel(eventLabel)
                .eventValue(eventValue.toString())
                .build();

        return measureProtocolRequest.executeRequest();
    }

    public boolean sendEvent(String category,
                             String action,
                             String eventLabel,
                             Integer eventValue) {
        MeasureProtocolRequest measureProtocolRequest = new MeasureProtocolRequest.Builder()
                .clientId(CLIENT_ID)
                .protocolVersion(PROTOCOL_VERSION)
                .applicationName(appName)
                .applicationVersion(appVersion)
                .trackingCode(trackingCode)
                .hitType(GaParameterConstants.EVENT_HIT_TYPE)
                .eventCategory(category)
                .eventAction(action)
                .eventLabel(eventLabel)
                .eventValue(eventValue.toString())
                .build();

        return measureProtocolRequest.executeRequest();
    }

    public static GoogleAnalytic build(String appName,
                                       String trackingCode) {
        return new GoogleAnalytic(appName, trackingCode);
    }

    public static GoogleAnalytic build(String appName,
                                       String appVersion,
                                       String trackingCode) {
        return new GoogleAnalytic(appName, appVersion, trackingCode);
    }
}
