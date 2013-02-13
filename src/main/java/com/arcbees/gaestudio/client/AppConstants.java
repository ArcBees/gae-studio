package com.arcbees.gaestudio.client;

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

    String refresh();

    String create();

    String edit();

    String delete();

    String bytes();

    String kibibytesAbbreviation();

    String mebibytesAbbreviation();

    String gibibytesAbbreviation();

}
