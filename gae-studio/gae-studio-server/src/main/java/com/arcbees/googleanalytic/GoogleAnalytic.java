/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.googleanalytic;

public class GoogleAnalytic {
    private static final String PROTOCOL_VERSION = "1";

    private final String appName;
    private final String appVersion;
    private final String trackingCode;
    private final String clientId;

    private GoogleAnalytic(String clientId,
                           String trackingCode,
                           String appName,
                           String appVersion) {
        this.appName = appName;
        this.appVersion = appVersion;
        this.trackingCode = trackingCode;
        this.clientId = clientId;
    }

    public boolean trackEvent(String eventCategory,
                              String eventAction) {
        MeasureProtocolRequest measureProtocolRequest = new MeasureProtocolRequest.Builder()
                .protocolVersion(PROTOCOL_VERSION)
                .clientId(clientId)
                .applicationName(appName)
                .applicationVersion(appVersion)
                .trackingCode(trackingCode)
                .hitType(GaParameterConstants.EVENT_HIT_TYPE)
                .eventCategory(eventCategory)
                .eventAction(eventAction)
                .build();

        return measureProtocolRequest.executeRequest();
    }

    public boolean trackEvent(String category,
                              String action,
                              String eventLabel) {
        MeasureProtocolRequest measureProtocolRequest = new MeasureProtocolRequest.Builder()
                .protocolVersion(PROTOCOL_VERSION)
                .clientId(clientId)
                .applicationName(appName)
                .applicationVersion(appVersion)
                .trackingCode(trackingCode)
                .hitType(GaParameterConstants.EVENT_HIT_TYPE)
                .eventCategory(category)
                .eventAction(action)
                .eventLabel(eventLabel)
                .build();

        return measureProtocolRequest.executeRequest();
    }

    public static GoogleAnalytic build(String clientId,
                                       String trackingCode,
                                       String appName,
                                       String appVersion) {
        return new GoogleAnalytic(clientId, trackingCode, appName, appVersion);
    }
}
