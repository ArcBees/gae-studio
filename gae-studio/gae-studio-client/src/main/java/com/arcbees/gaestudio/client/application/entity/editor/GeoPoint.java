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
