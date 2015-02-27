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
