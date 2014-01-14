/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.resources;

import com.google.gwt.i18n.client.Constants;

public interface AppConstants extends Constants {
    String filterByRequest();

    String filterByMethod();

    String filterByType();

    String getOperationType();

    String putOperationType();

    String queryOperationType();

    String deleteOperationType();

    String unknownOperationType();

    String record();

    String stop();

    String clear();

    String errorWhileGettingNewDbOperationRecords();

    String chooseAnEntity();

    String errorUnableToGenerateEmptyJson();

    String errorEntityDelete();

    String successEntityDelete();

    String successEntitiesDelete();

    String refresh();

    String create();

    String edit();

    String delete();

    String deleteAllOfKind();

    String deleteAll();

    String bytes();

    String kibibytesAbbreviation();

    String mebibytesAbbreviation();

    String gibibytesAbbreviation();

    String failedGettingEntity();

    String unableToRegister();

    String unableToLogin();

    String loggedInSuccessfully();

    String passwordReset();

    String invalidFields();

    String invalidProtocolOrHost();

    String invalidJson();

    String failedRegistration();

    String successfulRegistration();

    String validLicense();

    String invalidLatitude();

    String invalidLongitude();

    String invalidRating();

    String wrongPwdOrEmail();

    String oops();
}
