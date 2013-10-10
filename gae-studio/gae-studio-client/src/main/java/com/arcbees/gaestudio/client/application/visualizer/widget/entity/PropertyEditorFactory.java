/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import java.util.Date;

import javax.inject.Named;

import com.google.gwt.json.client.JSONValue;

public interface PropertyEditorFactory {
    PropertyEditor<String> createStringEditor(String key, JSONValue property);

    PropertyEditor<Boolean> createBooleanEditor(String key, JSONValue property);

    PropertyEditor<Long> createNumericEditor(String key, JSONValue property);

    PropertyEditor<Double> createFloatingEditor(String key, JSONValue property);

    PropertyEditor<Date> createDateEditor(String key, JSONValue property);

    @Named("POSTAL_ADDRESS") PropertyEditor<String> createPostalAddressEditor(String key, JSONValue property);

    @Named("CATEGORY") PropertyEditor<String> createCategoryEditor(String key, JSONValue property);

    @Named("LINK") PropertyEditor<String> createLinkEditor(String key, JSONValue property);

    @Named("EMAIL") PropertyEditor<String> createEmailEditor(String key, JSONValue property);

    @Named("PHONE_NUMBER") PropertyEditor<String> createPhoneNumberEditor(String key, JSONValue property);

    @Named("RATING") PropertyEditor<Long> createRatingEditor(String key, JSONValue property);

    PropertyEditor<?> createRawEditor(String key, JSONValue property);
}
