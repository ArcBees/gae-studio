/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.util;

import com.google.gwt.i18n.client.NumberFormat;

public class TimeNumberFormat {
    public static NumberFormat getFormat(){
        return NumberFormat.getFormat("0.000s");
    }
}
