package com.arcbees.gaestudio.client.formatters;

import com.google.gwt.i18n.client.NumberFormat;

public class BytesFormatter {

    private static final int ONE_KB = 1024;
    private static final int ONE_MB = ONE_KB * 1024;
    private static final int ONE_GB = ONE_MB * 1024;
    private static final String SIZE_PATTERN = "0.0";

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
        return bytes + " bytes";
    }

    private String showInKilobytes(Integer bytes) {
        return convert(bytes, ONE_KB) + " Kb";
    }

    private String showInMegabytes(Integer bytes) {
        return convert(bytes, ONE_MB) + " Mb";
    }

    private String showInGigabytes(Integer bytes) {
        return convert(bytes, ONE_GB) + " Gb";
    }

    private String convert(Integer bytes, Integer factor) {
        NumberFormat formatter = NumberFormat.getFormat(SIZE_PATTERN);
        Double converted = (double) bytes / factor;

        return formatter.format(converted);
    }

}
