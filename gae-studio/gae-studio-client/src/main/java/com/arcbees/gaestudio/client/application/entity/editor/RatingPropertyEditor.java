/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.entity.editor;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppConstants;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.assistedinject.Assisted;

public class RatingPropertyEditor extends LongPropertyEditor {
    private final AppConstants appConstants;
    private final RatingValidator ratingValidator;

    @Inject
    RatingPropertyEditor(AppConstants appConstants,
                         RatingValidator ratingValidator,
                         @Assisted String key,
                         @Assisted JSONValue property) {
        super(key, property);

        this.appConstants = appConstants;
        this.ratingValidator = ratingValidator;
    }

    @Override
    protected void showErrors() {
        showError(appConstants.invalidRating());
    }

    @Override
    protected boolean validate() {
        return ratingValidator.isRatingValid(getValue());
    }
}
