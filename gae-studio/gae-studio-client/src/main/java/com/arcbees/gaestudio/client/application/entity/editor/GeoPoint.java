/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;

public class GeoPoint {
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private Float latitude;
    private Float longitude;

    public GeoPoint(
            Float latitude,
            Float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static GeoPoint fromJsonObject(JSONObject jsonObject) {
        Float latitude = (float) jsonObject.get(LATITUDE).isNumber().doubleValue();
        Float longitude = (float) jsonObject.get(LONGITUDE).isNumber().doubleValue();

        return new GeoPoint(latitude, longitude);
    }

    public JSONObject asJsonObject() {
        JSONObject object = new JSONObject();
        object.put(LATITUDE, new JSONNumber(getLatitude()));
        object.put(LONGITUDE, new JSONNumber(getLongitude()));

        return object;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}
