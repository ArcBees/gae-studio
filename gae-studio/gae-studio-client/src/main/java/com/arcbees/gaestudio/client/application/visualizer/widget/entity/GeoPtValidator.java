/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

public class GeoPtValidator {
    public boolean isLatitudeValid(Double latitude) {
        return -90d <= latitude && latitude <= 90d;
    }

    public boolean isLongitudeValid(Double longitude) {
        return -180d <= longitude && longitude <= 180d;
    }
}
