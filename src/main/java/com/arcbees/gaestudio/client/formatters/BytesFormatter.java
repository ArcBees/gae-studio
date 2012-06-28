package com.arcbees.gaestudio.client.formatters;

import com.arcbees.gaestudio.client.MyConstants;
import com.google.gwt.i18n.client.NumberFormat;

import javax.inject.Inject;

public class BytesFormatter {

    private static final int ONE_KB = 1024;
    private static final int ONE_MB = ONE_KB * 1024;
    private static final int ONE_GB = ONE_MB * 1024;
    private static final String SIZE_PATTERN = "0.0";

    private final MyConstants myConstants;

    @Inject
    public BytesFormatter(final MyConstants myConstants) {
        this.myConstants = myConstants;
    }

    public String format(Integer bytes) {
        if (bytes < ONE_KB) {
            return showInBytes(bytes);
        } else if (bytes < ONE_MB) {
            return showInKilobytes(bytes);
        } else if (bytes < ONE_GB) {
            return showInMegabytes(bytes);
        } else {
            return showInGigabytes(bytes);
        }
    }

    private String showInBytes(Integer bytes) {
        return bytes + " " + myConstants.bytes();
    }

    private String showInKilobytes(Integer bytes) {
        return convert(bytes, ONE_KB) + " " + myConstants.kilobytesAbbreviation();
    }

    private String showInMegabytes(Integer bytes) {
        return convert(bytes, ONE_MB) + " " + myConstants.megabytesAbbreviation();
    }

    private String showInGigabytes(Integer bytes) {
        return convert(bytes, ONE_GB) + " " + myConstants.gigabytesAbbreviation();
    }

    private String convert(Integer bytes, Integer factor) {
        NumberFormat formatter = NumberFormat.getFormat(SIZE_PATTERN);
        Double converted = (double) bytes / factor;

        return formatter.format(converted);
    }

}
