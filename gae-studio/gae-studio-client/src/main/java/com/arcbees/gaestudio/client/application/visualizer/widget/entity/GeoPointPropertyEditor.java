/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.parseJsonValueWithMetadata;

public class GeoPointPropertyEditor extends AbstractPropertyEditor<GeoPoint> {
    interface Binder extends UiBinder<Widget, GeoPointPropertyEditor> {}

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    @UiField
    DoubleBox latitude;
    @UiField
    DoubleBox longitude;

    private final JSONValue property;

    @Inject
    GeoPointPropertyEditor(Binder uiBinder,
                           @Assisted String key,
                           @Assisted JSONValue property) {
        super(key);

        this.property = property;

        initFormWidget(uiBinder.createAndBindUi(this));

        latitude.getElement().setAttribute("placeholder", "Lat");
        longitude.getElement().setAttribute("placeholder", "Lng");

        setInitialValue();
    }

    @Override
    public JSONValue getJsonValue() {
        JSONObject object = new JSONObject();
        object.put(LATITUDE, new JSONNumber(getLatitude()));
        object.put(LONGITUDE, new JSONNumber(getLongitude()));

        return parseJsonValueWithMetadata(object, PropertyType.GEO_PT, PropertyUtil.isPropertyIndexed(property));
    }

    @Override
    public void setValue(GeoPoint geoPoint) {
        latitude.setValue((double) geoPoint.getLatitude());
        longitude.setValue((double) geoPoint.getLongitude());
    }

    @Override
    public GeoPoint getValue() {
        return new GeoPoint(getLatitude(), getLongitude());
    }

    private Float getLatitude() {
        Double value = latitude.getValue();
        return value == null ? null : value.floatValue();
    }

    private Float getLongitude() {
        Double value = longitude.getValue();
        return value == null ? null : value.floatValue();
    }

    private void setInitialValue() {
        JSONObject geoPtObject = PropertyUtil.getPropertyValue(property).isObject();
        Float latitude = (float) geoPtObject.get(LATITUDE).isNumber().doubleValue();
        Float longitude = (float) geoPtObject.get(LONGITUDE).isNumber().doubleValue();

        setValue(new GeoPoint(latitude, longitude));
    }
}
