/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import java.util.Date;

import javax.inject.Inject;

import com.arcbees.gaestudio.shared.Constants;
import com.arcbees.gaestudio.shared.PropertyType;
import com.google.common.base.Strings;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.entity.editor.PropertyUtil.parseJsonValueWithMetadata;
import static com.google.gwt.query.client.GQuery.$;

public class DatePropertyEditor extends AbstractPropertyEditor<Date> {
    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat(Constants.JSON_DATE_FORMAT);

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
    protected boolean validate() {
        return !$(dateBox).hasClass("dateBoxFormatError");
    }

    private Date getValue() {
        return dateBox.getValue();
    }

    private void setValue(Date value) {
        dateBox.setValue(value);
    }

    private String getFormattedDate() {
        String formattedDate = "";
        if (getValue() != null) {
            formattedDate = DATE_FORMAT.format(getValue());
        }

        return formattedDate;
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
