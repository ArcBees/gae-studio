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

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.shared.PropertyType;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.parseJsonValueWithMetadata;

public class GeoPointPropertyEditor extends AbstractPropertyEditor<GeoPoint> {
    interface Binder extends UiBinder<Widget, GeoPointPropertyEditor> {
    }

    @UiField
    DoubleBox latitude;
    @UiField
    DoubleBox longitude;

    private final JSONValue property;
    private final GeoPtValidator geoPtValidator;
    private final AppConstants appConstants;

    @Inject
    GeoPointPropertyEditor(
            Binder uiBinder,
            GeoPtValidator geoPtValidator,
            AppConstants appConstants,
            @Assisted String key,
            @Assisted JSONValue property) {
        super(key);

        this.property = property;
        this.geoPtValidator = geoPtValidator;
        this.appConstants = appConstants;

        initFormWidget(uiBinder.createAndBindUi(this));
        setInitialValue();
    }

    @Override
    public JSONValue getJsonValue() {
        JSONObject object = getValue().asJsonObject();

        return parseJsonValueWithMetadata(object, PropertyType.GEO_PT, PropertyUtil.isPropertyIndexed(property));
    }

    @Override
    protected void showErrors() {
        Boolean isLatitudeValid = isLatitudeValid();
        Boolean isLongitudeValid = isLongitudeValid();

        Collection<String> errorTokens = new ArrayList<String>();

        if (!isLatitudeValid) {
            errorTokens.add(appConstants.invalidLatitude());
        }

        if (!isLongitudeValid) {
            errorTokens.add(appConstants.invalidLongitude());
        }

        showErrors(errorTokens);
    }

    @Override
    protected boolean validate() {
        return isLatitudeValid() && isLongitudeValid();
    }

    private GeoPoint getValue() {
        return new GeoPoint(getLatitude(), getLongitude());
    }

    private void setValue(GeoPoint geoPoint) {
        latitude.setValue((double) geoPoint.getLatitude());
        longitude.setValue((double) geoPoint.getLongitude());
    }

    private boolean isLongitudeValid() {
        return geoPtValidator.isLongitudeValid(longitude.getValue());
    }

    private boolean isLatitudeValid() {
        return geoPtValidator.isLatitudeValid(latitude.getValue());
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

        setValue(GeoPoint.fromJsonObject(geoPtObject));
    }
}
