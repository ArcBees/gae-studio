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

    String deselect();

    String bytes();

    String kibibytesAbbreviation();

    String mebibytesAbbreviation();

    String gibibytesAbbreviation();

    String failedGettingEntity();

    String unableToRegister();

    String registerSuccessfull();

    String alreadyRegistred();

    String unableToLogin();

    String loggedInSuccessfully();

    String passwordReset();

    String invalidFields();

    String invalidProtocolOrHost();

    String invalidJson();

    String keyDoesNotExist();

    String successfulRegistration();

    String validLicense();

    String invalidLatitude();

    String invalidLongitude();

    String invalidRating();

    String wrongPwdOrEmail();

    String oops();

    String registerKey();

    String passwordDontMatch();

    String allFieldsAreRequired();

    String errorSavingChanges();

    String enterAKey();

    String invalidEmail();

    String emailIsRequired();

    String wrongGqlRequest();

    String noEntitiesMatchRequest();

    String missingSelectInRequest();

    String somethingWentWrong();

    String thankYou();

    String entitySaved();

    String entitiesSaved();

    String importSuccess();

    String unindexed();
}
