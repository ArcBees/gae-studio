/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.formatters;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppConstants;
import com.google.gwt.i18n.client.NumberFormat;

public class BytesFormatter {
    private static final int ONE_KiB = 1024;
    private static final int ONE_MiB = ONE_KiB * 1024;
    private static final int ONE_GiB = ONE_MiB * 1024;
    private static final String SIZE_PATTERN = "0.0";

    private final AppConstants myConstants;

    @Inject
    public BytesFormatter(
            AppConstants myConstants) {
        this.myConstants = myConstants;
    }

    public String format(Integer bytes) {
        if (bytes < ONE_KiB) {
            return showInBytes(bytes);
        } else if (bytes < ONE_MiB) {
            return showInKilobytes(bytes);
        } else if (bytes < ONE_GiB) {
            return showInMegabytes(bytes);
        } else {
            return showInGigabytes(bytes);
        }
    }

    private String showInBytes(Integer bytes) {
        return bytes + " " + myConstants.bytes();
    }

    private String showInKilobytes(Integer bytes) {
        return convert(bytes, ONE_KiB) + " " + myConstants.kibibytesAbbreviation();
    }

    private String showInMegabytes(Integer bytes) {
        return convert(bytes, ONE_MiB) + " " + myConstants.mebibytesAbbreviation();
    }

    private String showInGigabytes(Integer bytes) {
        return convert(bytes, ONE_GiB) + " " + myConstants.gibibytesAbbreviation();
    }

    private String convert(Integer bytes, Integer factor) {
        NumberFormat formatter = NumberFormat.getFormat(SIZE_PATTERN);
        Double converted = (double) bytes / factor;

        return formatter.format(converted);
    }
}
