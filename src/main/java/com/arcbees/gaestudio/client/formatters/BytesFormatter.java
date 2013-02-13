package com.arcbees.gaestudio.client.formatters;

import com.arcbees.gaestudio.client.resources.AppConstants;
import com.google.gwt.i18n.client.NumberFormat;

import javax.inject.Inject;

public class BytesFormatter {

    private static final int ONE_KiB = 1024;
    private static final int ONE_MiB = ONE_KiB * 1024;
    private static final int ONE_GiB = ONE_MiB * 1024;
    private static final String SIZE_PATTERN = "0.0";

    private final AppConstants myConstants;

    @Inject
    public BytesFormatter(final AppConstants myConstants) {
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
