/**
 * Copyright 2011 ArcBees Inc.
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

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import java.util.Date;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.PropertyType;
import com.google.common.base.Strings;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.visualizer.widget.entity.PropertyUtil.parseJsonValueWithMetadata;

public class DatePropertyEditor extends AbstractPropertyEditor<Date> {
    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("MMM d, y h:mm:ss a");

    private final DateBox dateBox;
    private final JSONValue property;

    @Inject
    DatePropertyEditor(@Assisted String key,
                       @Assisted JSONValue property) {
        super(key);

        this.property = property;

        dateBox = new DateBox();
        dateBox.setFormat(new DefaultFormat(DATE_FORMAT));
        // TODO: Override class .dateBoxPopup
        dateBox.getDatePicker().getElement().getParentElement().getParentElement().getStyle().setZIndex(150);

        initFormWidget(dateBox);
        setValue(parseDate(PropertyUtil.getPropertyValue(property).isString().stringValue()));
    }

    @Override
    public JSONValue getJsonValue() {
        JSONString value = new JSONString(getFormattedDate());
        return parseJsonValueWithMetadata(value, PropertyType.DATE, PropertyUtil.isPropertyIndexed(property));
    }

    @Override
    public void setValue(Date value) {
        dateBox.setValue(value);
    }

    @Override
    public Date getValue() {
        return dateBox.getValue();
    }

    private String getFormattedDate() {
        return DATE_FORMAT.format(getValue());
    }

    private Date parseDate(String parsedDate) {
        Date date = new Date();

        if (!Strings.isNullOrEmpty(parsedDate)) {
            try {
                date = DATE_FORMAT.parse(parsedDate);
            } catch (IllegalArgumentException ignored) {
            }
        }

        return date;
    }
}
